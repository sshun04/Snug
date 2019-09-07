package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post


class HomePostsFragmentUseCase {
    private val fireStoreRepository: DataBaseRepository = FireStoreDatabaseRepository()
    private var previousPost: Post = Post(sentiScore = -0.4f)
    private var previousRange: Int = 3

    suspend fun requestPostsByScore(targetScore: Int): List<Post> {
        var max = 0F
        var min = 0F
        var progressRange = 3

        val result = when {
            targetScore >= 18 -> {
                min = 0.7f
                progressRange = 5
                if (progressRange != previousRange) {
                    previousPost = Post(sentiScore = min)
                }
                fireStoreRepository.loadScoreRangedCollectionAscend(post = previousPost)
            }

            targetScore in 13 until 18 -> {
                min = 0.4f
                progressRange = 4
                if (progressRange != previousRange) {
                    previousPost = Post(sentiScore = min)
                }
                fireStoreRepository.loadScoreRangedCollectionAscend(post = previousPost)
            }

            targetScore in 8..12 -> {
                min = -0.4f
                progressRange = 3
                if (progressRange != previousRange) {
                    previousPost = Post(sentiScore = min)
                }
                fireStoreRepository.loadPositiveTimeLineCollection(previousPost.date)
            }
            targetScore in 3..7 -> {
                max = -0.2f
                progressRange = 2
                if (progressRange != previousRange) {
                    previousPost = Post(sentiScore = max)
                }
                fireStoreRepository.loadScoreRangedCollectionDescend(post = previousPost)

            }

            targetScore <= 2 -> {
                max = -0.5f
                progressRange = 1
                if (progressRange != previousRange) {
                    previousPost = Post(sentiScore = max)
                }
                fireStoreRepository.loadScoreRangedCollectionDescend(post = previousPost)
            }
            else -> throw Exception("SeekBar progress is out of range")
        }

        previousRange = progressRange

        if (result.isNotEmpty()) previousPost = result.last()

        return result
    }

    fun resetValues() {
        previousPost = Post(sentiScore = -0.4f)
        previousRange = 3
    }
}