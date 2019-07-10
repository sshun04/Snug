package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post

class DetailPostsFragmentUsecase {
   private val fireStoreRepository = FireStoreDatabaseRepository()

    suspend fun loadNextRelatedPosts(post: Post,previousPost:Post?):List<Post>{
        val basePost = previousPost?: post
        return fireStoreRepository.loadNextSortedCollection(basePost,0.0f,0.0f)
    }
}