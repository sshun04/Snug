package com.shojishunsuke.kibunnsns.domain.use_case

import com.shojishunsuke.kibunnsns.data.repository.impl.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.domain.model.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPostsFragmentUseCase(basePost: Post) {
    private val fireStoreRepository: FireStoreDatabaseRepository = FireStoreDatabaseRepository()
    private var hasMoreSameActPost: Boolean = true
    private var sameActPrevPost: Post
    private var wideRangePrevPost: Post

    init {
        sameActPrevPost = basePost
        wideRangePrevPost = Post(sentiScore = basePost.sentiScore)
    }

    suspend fun loadPosts(): List<Post> {
        val wideRangeCollection = loadWideRangeCollection()
        val result = mutableListOf<Post>().apply {
            addAll(wideRangeCollection)
        }
        if (hasMoreSameActPost) {
            val sameActCollection = loadSameActCollection()
            result.addAll(sameActCollection)
        }

        return result.shuffled()
    }

    fun increaseView(postId: String) {
        GlobalScope.launch { fireStoreRepository.increaseViews(postId) }
    }

    private suspend fun loadWideRangeCollection(): List<Post> {
        val posts = fireStoreRepository.loadScoreRangedCollectionAscend(post = wideRangePrevPost)
        if (posts.isNotEmpty()) wideRangePrevPost = posts.last()
        return posts
    }

    private suspend fun loadSameActCollection(): List<Post> {
        val posts = fireStoreRepository.loadSpecificSortedNextCollection(sameActPrevPost)
        if (posts.isNotEmpty()) sameActPrevPost = posts.last()
        if (posts.size < 12) hasMoreSameActPost = false

        return posts
    }
}