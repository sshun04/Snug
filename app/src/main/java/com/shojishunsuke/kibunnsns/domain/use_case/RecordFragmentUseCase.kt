package com.shojishunsuke.kibunnsns.domain.use_case

import android.graphics.Bitmap
import android.net.Uri
import com.shojishunsuke.kibunnsns.data.repository.impl.CloudStorageRepository
import com.shojishunsuke.kibunnsns.data.repository.impl.FirebaseUserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordFragmentUseCase(uploadListener: CloudStorageRepository.ImageUploadListener) {
    private val userInfoRepository: FirebaseUserRepository = FirebaseUserRepository()
    private val cloutStorageRepository: CloudStorageRepository =
        CloudStorageRepository(uploadListener)

    fun saveUserName(name: String) {
        userInfoRepository.updateUserName(name)
    }

    fun saveIconToCloud(bitmap: Bitmap) {
        GlobalScope.launch {
            cloutStorageRepository.uploadImage(bitmap)
        }
    }

    suspend fun saveLocalPhotoUri(uri: Uri) {
        userInfoRepository.updateUserPhoto(uri)
    }

    fun getIconUrl(): String = userInfoRepository.getUserPhotoUri().toString()

    fun getUserName(): String {
        return userInfoRepository.getUserName()
    }
}