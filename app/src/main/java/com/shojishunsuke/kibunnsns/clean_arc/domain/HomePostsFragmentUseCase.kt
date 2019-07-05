package com.shojishunsuke.kibunnsns.clean_arc.domain

import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.shojishunsuke.kibunnsns.clean_arc.data.CloudStorageRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.EmojiRepositoy
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking

class HomePostsFragmentUseCase : CloudStorageRepository.ImageUploadListener {
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private val cloudStorageRepository = CloudStorageRepository(this)
    private val emojiRepositoy = EmojiRepositoy()
    val smilyEmojis = emojiRepositoy.smileys

    suspend fun getPosts(fieldName: String, params: Any): List<Post> = runBlocking {
        return@runBlocking fireStoreRepository.loadWholeCollection()
    }

    fun getIconStorageRef(uriString: String): StorageReference {
        return cloudStorageRepository.getStorageRefByUri(uriString)
    }

    fun getSmilyes(sentiScore: Float): String {
        return when {
            sentiScore > 0.6 -> smilyEmojis[0]
            sentiScore <= 0.6 && sentiScore > 0.2 -> smilyEmojis[1]
            sentiScore <= 0.2 && sentiScore > -0.2 -> smilyEmojis[2]
            sentiScore <= -0.2 && sentiScore >= -0.6 -> smilyEmojis[3]
            sentiScore < -0.6 -> smilyEmojis[4]
            else -> smilyEmojis[2]
        }
    }

    suspend fun loadRelatedPosts(post: Post): List<Post> = runBlocking {
        return@runBlocking fireStoreRepository.loadWholeCollection()
//            post.sentiScore,
//            post.magnitude,
//            post.keyWord,
//            post.actID

    }

    override suspend fun onUploadTaskComplete(result: Uri) {}

}