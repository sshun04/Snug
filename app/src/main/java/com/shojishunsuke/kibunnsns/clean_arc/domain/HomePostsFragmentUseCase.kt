package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking

class HomePostsFragmentUseCase {
    private val fireStoreRepository = FireStoreDataBaseRepository()

    suspend fun getPosts(fieldName: String, params: Any): List<Post> = runBlocking {
        return@runBlocking fireStoreRepository.loadWholeCollection()
    }

}