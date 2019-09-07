package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostItemUsecase {
    private val fireStoreRepository: FireStoreDatabaseRepository = FireStoreDatabaseRepository()
    fun increaseView(postId: String) {
        GlobalScope.launch { fireStoreRepository.increaseViews(postId) }
    }
}