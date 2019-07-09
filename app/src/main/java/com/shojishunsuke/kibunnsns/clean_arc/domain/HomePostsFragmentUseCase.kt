package com.shojishunsuke.kibunnsns.clean_arc.domain

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.shojishunsuke.kibunnsns.clean_arc.data.CloudStorageRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post


class HomePostsFragmentUseCase : CloudStorageRepository.ImageUploadListener {
    private val fireStoreRepository = FireStoreDatabaseRepository()


    fun getPagingOptionBuilder(lifecycleOwner:LifecycleOwner):FirestorePagingOptions<Post>{
        return fireStoreRepository.loadPagingOptions(lifecycleOwner)
    }

    override suspend fun onUploadTaskComplete(result: Uri) {}

    suspend fun load16items():List<Post>{
        return fireStoreRepository.loadWholeCollection()
    }

}