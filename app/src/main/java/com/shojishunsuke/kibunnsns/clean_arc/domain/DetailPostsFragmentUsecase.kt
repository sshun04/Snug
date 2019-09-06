package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.CloudPost

class DetailPostsFragmentUsecase(baseCloudPost: CloudPost) {
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private var hasMoreSameActPost = true
    private var sameActPrevCloudPost: CloudPost
    private var wideRangePrevCloudPost: CloudPost

    init {
        sameActPrevCloudPost = baseCloudPost
        wideRangePrevCloudPost = CloudPost(sentiScore = baseCloudPost.sentiScore)
    }

    suspend fun loadPosts(): List<CloudPost> {
        val wideRangeCollection = loadWideRangeCollection()
        val result = mutableListOf<CloudPost>().apply {
            addAll(wideRangeCollection)
        }
        if (hasMoreSameActPost) {
            val sameActCollection = loadSameActCollection()
            result.addAll(sameActCollection)
        }

        return result.shuffled()
    }

    private suspend fun loadWideRangeCollection(): List<CloudPost> {
        val posts = fireStoreRepository.loadScoreRangedCollectionAscend(post = wideRangePrevCloudPost)
        if (posts.isNotEmpty()) wideRangePrevCloudPost = posts.last() as CloudPost
        return posts as List<CloudPost>
    }

    private suspend fun loadSameActCollection(): List<CloudPost> {
        val posts = fireStoreRepository.loadSpecificSortedNextCollection(sameActPrevCloudPost)
        if (posts.isNotEmpty()) sameActPrevCloudPost = posts.last() as CloudPost
        if (posts.size < 12) hasMoreSameActPost = false

        return posts as List<CloudPost>
    }
}