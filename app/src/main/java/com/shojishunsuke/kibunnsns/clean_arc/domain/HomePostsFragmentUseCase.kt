package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import java.util.*


class HomePostsFragmentUseCase {
    private val fireStoreRepository = FireStoreDatabaseRepository()

    suspend fun requestPostsByScore(targetScore: Int, previousPost: Post?): List<Post> {
        var max = 0F
        var min = 0F
        val date = previousPost?.date ?: Date()

        when {
            targetScore >= 9 -> {
                min = 0.7f
                max = 1.0f
            }

            targetScore in 4 until 9 -> {
                min = 0.4f
                max = 1.0f
            }

            targetScore in -3..3 -> {
               min = -0.4f
                max = 1f
            }

            targetScore in -4 until -9 -> {
                min = -1.0f
                max = 0.3f

            }

            targetScore <= -9 -> {
                min = -1.0f
                max = -0.5f

            }
            else -> {

            }
        }
        val result = fireStoreRepository.loadScoreRangedCollection(min = min,max = max,limit = 30, date = date)

        return result

    }


}