package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.EmojiRepositoy
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataConfigRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking

class PostDialogUseCase(
    private val dataConfigRepository: DataConfigRepository,
    private val analysisRepository: LanguageAnalysisRepository
) {
    private val emojiRepository = EmojiRepositoy()
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private val userInfoRepository = FirebaseUserRepository()


    suspend fun generatePost(content: String, actID: String): Post = runBlocking {
        dataConfigRepository.updateCollection(actID)

        val userName = userInfoRepository.getUserName()
        val userId = userInfoRepository.getUserId()
        val iconUri = userInfoRepository.getUserPhotoUri()
        val analysisResult = analysisRepository.analyzeText(content)
        val sentiScore: Float = analysisResult.first
        val magnitude: Float = analysisResult.second
        val category: String = analysisResult.third

        val post = Post(
            userName = userName,
            userId = userId,
            iconPhotoLink = "$iconUri",
            contentText = content,
            sentiScore = sentiScore,
            magnitude = magnitude,
            actID = actID,
            keyWord = category
        )

        runBlocking {
            fireStoreRepository.savePost(post)
        }
        return@runBlocking post
    }

    fun loadWholeEmoji(): List<String> = emojiRepository.loadWholeEmoji()

    fun loadCurrentEmoji(): List<String> = dataConfigRepository.getLatestCollection()

    fun updateCurrentEmoji(emojiCode:String){
        dataConfigRepository.updateCollection(emojiCode)
    }
}