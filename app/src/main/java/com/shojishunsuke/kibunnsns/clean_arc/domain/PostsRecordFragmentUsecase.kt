package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.model.Post
import java.util.*

class PostsRecordFragmentUsecase {

    private val userRepository = FirebaseUserRepository()
    private val userId = userRepository.getUserId()
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private lateinit var listLastPost: Post

    suspend fun loadPosts(): List<Post> {
        val currentDate = Calendar.getInstance().time
        val oldDate = getRangeEndDate()
        val posts = fireStoreRepository.loadDateRangedCollection(userId, oldDate, currentDate, 30)
        if (posts.isNotEmpty()) listLastPost = posts.last()
        return posts
    }

    private fun getRangeEndDate(): Date {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MONTH, -3)
        }
        return calendar.time
    }
}