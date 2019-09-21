package com.shojishunsuke.kibunnsns.domain.use_case

import com.shojishunsuke.kibunnsns.data.repository.impl.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.domain.model.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPostsFragmentUseCase(private val basePost: Post) {
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
            result.shuffle()
        }

        return result.distinct()
    }

    fun increaseView(postId: String) {
        GlobalScope.launch { fireStoreRepository.increaseViews(postId) }
    }

    private suspend fun loadWideRangeCollection(): List<Post> {
        val posts =
            fireStoreRepository.loadScoreRangedCollectionAscend(post = wideRangePrevPost).apply {
                removeIf { post -> post.postId == basePost.postId }
                removeIf { post -> post.actID == basePost.actID }
            }
        if (posts.isNotEmpty()) wideRangePrevPost = posts.last()

        return posts
    }

    private suspend fun loadSameActCollection(): List<Post> {
        val posts = fireStoreRepository.loadSpecificSortedNextCollection(sameActPrevPost).apply {
            removeIf { post -> post.postId == basePost.postId }
        }
        if (posts.isNotEmpty()) sameActPrevPost = posts.last()
        if (posts.size < 12) hasMoreSameActPost = false

        return posts
    }
}