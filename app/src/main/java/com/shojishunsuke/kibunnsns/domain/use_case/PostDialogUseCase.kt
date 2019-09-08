package com.shojishunsuke.kibunnsns.domain.use_case

import com.shojishunsuke.kibunnsns.data.repository.impl.EmojiRepositoy
import com.shojishunsuke.kibunnsns.data.repository.impl.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.data.repository.impl.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.data.repository.impl.RoomPostDateRepository
import com.shojishunsuke.kibunnsns.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.data.repository.LanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.data.repository.LocalDataBaseRepository
import com.shojishunsuke.kibunnsns.domain.model.EmojiItem
import com.shojishunsuke.kibunnsns.domain.model.Post
import kotlinx.coroutines.runBlocking
import java.util.*

class PostDialogUseCase(
        private val localDataBaseRepository: LocalDataBaseRepository,
        private val analysisRepository: LanguageAnalysisRepository
) {
    private val emojiRepository: EmojiRepositoy = EmojiRepositoy()
    private val fireStoreRepository: DataBaseRepository = FireStoreDatabaseRepository()
    private val userInfoRepository: FirebaseUserRepository = FirebaseUserRepository()
    private val postDateRepository: RoomPostDateRepository = RoomPostDateRepository()

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