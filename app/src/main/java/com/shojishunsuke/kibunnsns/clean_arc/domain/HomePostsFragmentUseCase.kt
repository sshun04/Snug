package com.shojishunsuke.kibunnsns.clean_arc.domain

import android.net.Uri
import com.shojishunsuke.kibunnsns.clean_arc.data.CloudStorageRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import java.util.*


class HomePostsFragmentUseCase : CloudStorageRepository.ImageUploadListener {
    private val fireStoreRepository = FireStoreDatabaseRepository()

    override suspend fun onUploadTaskComplete(result: Uri) {}

    suspend fun load16items(post: Post?): List<Post> {
        val previousPost = post ?: Post(date = Date())
        return fireStoreRepository.loadWideRangeNextCollection(previousPost)
    }

    suspend fun loadFilteredPost(showNegative: Boolean): List<Post> {
        val baseSentiScore = if (showNegative) -1.0f else -0.3f
        val basePost = Post(sentiScore = baseSentiScore)

        return fireStoreRepository.loadWideRangeNextCollection(basePost)

    }

}