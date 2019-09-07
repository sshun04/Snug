package com.shojishunsuke.kibunnsns.domain.use_case

import com.shojishunsuke.kibunnsns.data.repository.impl.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.data.repository.impl.RoomPostDateRepository
import com.shojishunsuke.kibunnsns.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.domain.model.Post

class PostRecordItemUseCase {
    private val fireStoreRepository: DataBaseRepository = FireStoreDatabaseRepository()
    private val roomRepository: RoomPostDateRepository = RoomPostDateRepository()

    fun deletePostFromDatabase(post: Post) {
        fireStoreRepository.deleteItemFromDatabase(post)
        roomRepository.deleteDate(post.date)
    }
}