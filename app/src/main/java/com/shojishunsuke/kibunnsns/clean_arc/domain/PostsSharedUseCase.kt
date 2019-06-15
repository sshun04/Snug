package com.shojishunsuke.kibunnsns.clean_arc.domain


import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDataBaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking

class PostsSharedUseCase(private val analysisRepository: LanguageAnalysisRepository) {

    private val fireStoreRepository = FireStoreDataBaseRepository()

    suspend fun generatePost(content: String): Post = runBlocking {

        val sentiScore = analysisRepository.getScore(content)
        val post = Post(content, sentiScore, actID = "")

        runBlocking {
            fireStoreRepository.savePost(post)
        }

        return@runBlocking post
    }

    suspend fun loadRelatedPosts(post: Post): List<Post> {
//        TODO 関連した投稿を取得するアルゴリズムを実装

        return fireStoreRepository.loadFilteredCollection("sentiScore", post.sentiScore)
    }


}