package com.shojishunsuke.kibunnsns.domain.use_case

import com.shojishunsuke.kibunnsns.data.repository.impl.FireStoreDatabaseRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostItemUseCase {
    private val fireStoreRepository: FireStoreDatabaseRepository = FireStoreDatabaseRepository()
    fun increaseView(postId: String) {
        GlobalScope.launch { fireStoreRepository.increaseViews(postId) }
    }
}