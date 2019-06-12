package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post

class HomePostsFragmentUseCase {
    private val fireStoreRepository = FireStoreDataBaseRepository()

    fun getPosts(fieldName: String, params: Any): List<Post> =
        fireStoreRepository.getFilteredCollection(fieldName, params)

}