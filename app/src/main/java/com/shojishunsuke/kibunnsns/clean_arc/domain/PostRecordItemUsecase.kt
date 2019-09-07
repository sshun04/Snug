package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.RoomPostDateRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post

class PostRecordItemUsecase {
    private val fireStoreRepository: DataBaseRepository = FireStoreDatabaseRepository()
    private val roomRepository: RoomPostDateRepository = RoomPostDateRepository()

    fun deletePostFromDatabase(post: Post) {
        fireStoreRepository.deleteItemFromDatabase(post)
        roomRepository.deleteDate(post.date)
    }
}