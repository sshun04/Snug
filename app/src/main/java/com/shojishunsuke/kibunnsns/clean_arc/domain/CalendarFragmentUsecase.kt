package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.model.Post

class CalendarFragmentUsecase {
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private val userRepository = FirebaseUserRepository()
    private val userId = userRepository.getUserId()

    suspend fun loadPostsByDate(date: String): List<Post> {
        return fireStoreRepository.loadOwnCollectionsByDate(userId, date)
    }
}