package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.EmojiRepositoy
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking

class HomePostsFragmentUseCase {
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private val emojiRepositoy = EmojiRepositoy()
    val smilyEmojis = emojiRepositoy.smileys

    suspend fun getPosts(fieldName: String, params: Any): List<Post> = runBlocking {
        return@runBlocking fireStoreRepository.loadWholeCollection()
    }

    fun getSmilyes(sentiScore: Float): String {
        return when {
            sentiScore > 0.6 -> smilyEmojis[0]
            sentiScore <= 0.6 && sentiScore > 0.2 -> smilyEmojis[1]
            sentiScore <= 0.2 && sentiScore > -0.2 -> smilyEmojis[2]
            sentiScore <= -0.2 && sentiScore >= -0.6 -> smilyEmojis[3]
            sentiScore <-0.6 -> smilyEmojis[4]
            else -> smilyEmojis[2]
        }
    }

}