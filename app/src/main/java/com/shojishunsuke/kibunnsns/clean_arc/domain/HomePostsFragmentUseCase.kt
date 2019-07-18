package com.shojishunsuke.kibunnsns.clean_arc.domain

import android.net.Uri
import com.shojishunsuke.kibunnsns.clean_arc.data.CloudStorageRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import java.util.*


class HomePostsFragmentUseCase : CloudStorageRepository.ImageUploadListener {
    private val fireStoreRepository = FireStoreDatabaseRepository()

    override suspend fun onUploadTaskComplete(result: Uri) {}

    suspend fun load16items(showNegative: Boolean, post: Post?): List<Post> {
        val previousPost = post ?: Post(date = Date())
        return if (showNegative) fireStoreRepository.loadFromWholePosts(previousPost)
        else fireStoreRepository.loadPositivePostsOnly(previousPost)

    }



}