package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import java.lang.Exception


class HomePostsFragmentUseCase {
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private var previousPost = Post(sentiScore = -0.4f)
    private var previousRange = 3


    suspend fun requestPostsByScore(targetScore: Int): List<Post> {
        var max = 0F
        var min = 0F
        var progressRange = 3


      val result =  when {
            targetScore >= 8 -> {
                min = 0.7f
                progressRange = 5
                if (progressRange != previousRange){
                    previousPost = Post(sentiScore = min)
                }
                fireStoreRepository.loadScoreRangedCollectionPositive(post = previousPost)
            }

            targetScore in 4 until 8 -> {
                min = 0.4f
                progressRange = 4
                if (progressRange != previousRange){
                    previousPost = Post(sentiScore = min)
                }
                fireStoreRepository.loadScoreRangedCollectionPositive(post = previousPost)
            }

            targetScore in -3..3 -> {
                min = -0.4f
                progressRange = 3
                if (progressRange != previousRange) {
                    previousPost = Post(sentiScore = min)
                }
                fireStoreRepository.loadPositiveTimeLineCollection(previousPost.date)
            }
            targetScore in -7 .. -4 -> {
                max = -0.2f
                progressRange = 2
                if (progressRange != previousRange){
                    previousPost = Post(sentiScore = max)
                }
                fireStoreRepository.loadScoreRangedCollectionNegative(post = previousPost)

            }

            targetScore <= -8 -> {
                max = -0.5f
                progressRange = 1
                if (progressRange != previousRange){
                    previousPost = Post(sentiScore = max)
                }
                fireStoreRepository.loadScoreRangedCollectionNegative(post = previousPost)


            }
          else -> throw Exception("SeekBar progress is out of range")
        }

        previousRange = progressRange

        if (result.isNotEmpty())previousPost = result.last()

        return result

    }

   fun resetValues(){
        previousPost = Post(sentiScore = -0.4f)
        previousRange = 3

    }




}