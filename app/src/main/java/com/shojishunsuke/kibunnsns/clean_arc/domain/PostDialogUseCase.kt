package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.EmojiRepositoy
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.RoomPostDateRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LocalDataBaseRepository
import com.shojishunsuke.kibunnsns.model.EmojiItem
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking
import java.util.*

class PostDialogUseCase(
        private val localDataBaseRepository: LocalDataBaseRepository,
        private val analysisRepository: LanguageAnalysisRepository
) {
    private val emojiRepository = EmojiRepositoy()
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private val userInfoRepository = FirebaseUserRepository()
    private val postDateRepository = RoomPostDateRepository()

    suspend fun generatePost(content: String, emojiCode: String, date: Date): Post = runBlocking {

        if (emojiCode.isNotBlank()) {
            localDataBaseRepository.registerItem(emojiCode)
        }
        postDateRepository.registerDate(date)

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
                actID = emojiCode,
                keyWord = category,
                date = date
        )

        runBlocking {
            fireStoreRepository.savePost(post)
        }
        return@runBlocking post
    }

    fun loadWholeEmoji(): List<String> = emojiRepository.loadWholeEmoji()

    suspend fun loadCurrentEmoji(): List<String> {
        val defaultCollection: List<EmojiItem> =
                localDataBaseRepository.loadLatestCollection() as List<EmojiItem>
        val stringList = mutableListOf<String>()
        defaultCollection.forEach {
            stringList.add(it.emojiCode)
        }
        return stringList
    }
}