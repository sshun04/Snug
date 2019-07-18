package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import java.util.*


class HomePostsFragmentUseCase {
    private val fireStoreRepository = FireStoreDatabaseRepository()

    suspend fun requestPosts(hideNegative: Boolean, post: Post?): List<Post> {
        val previousPost = post ?: Post(date = Date())
        return if (hideNegative) fireStoreRepository.loadPositivePostsOnly(previousPost)
        else fireStoreRepository.loadFollowingCollection(previousPost)
    }
}