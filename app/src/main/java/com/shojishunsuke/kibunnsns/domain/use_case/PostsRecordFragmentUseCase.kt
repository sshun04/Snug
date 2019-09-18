package com.shojishunsuke.kibunnsns.domain.use_case

import com.shojishunsuke.kibunnsns.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.data.repository.impl.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.data.repository.impl.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.data.repository.impl.RoomPostDateRepository
import com.shojishunsuke.kibunnsns.domain.model.Post
import java.util.*

class PostsRecordFragmentUseCase {

    private val userRepository: FirebaseUserRepository = FirebaseUserRepository()
    private val userId: String = userRepository.getUserId()
    private val fireStoreRepository: DataBaseRepository = FireStoreDatabaseRepository()
    private val roomRepository: RoomPostDateRepository = RoomPostDateRepository()
    private lateinit var listLastPost: Post

    suspend fun loadPosts(): List<Post> {
        val currentDate = Calendar.getInstance().time
        val oldDate = getRangeEndDate()
        val posts = fireStoreRepository.loadDateRangedCollection(userId, oldDate, currentDate, 30)
        if (posts.isNotEmpty()) listLastPost = posts.last()
        return posts
    }

    fun deletePostFromDatabase(post: Post) {
        fireStoreRepository.deleteItemFromDatabase(post)
        roomRepository.deleteDate(post.date)
    }

    private fun getRangeEndDate(): Date {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MONTH, -3)
        }
        return calendar.time
    }
}