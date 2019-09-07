package com.shojishunsuke.kibunnsns.domain.use_case

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.shojishunsuke.kibunnsns.data.repository.impl.CloudStorageRepository
import com.shojishunsuke.kibunnsns.data.repository.impl.FirebaseUserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordFragmentUseCase(uploadListener: CloudStorageRepository.ImageUploadListener) {
    private val userInfoRepository: FirebaseUserRepository = FirebaseUserRepository()
    private val cloutStorageRepository: CloudStorageRepository = CloudStorageRepository(uploadListener)

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

    fun getIconStorageRef(): StorageReference {
        val uriString = userInfoRepository.getUserPhotoUri().toString()
        return cloutStorageRepository.getStorageRefByUri(uriString)
    }

    fun getUserName(): String {
        return userInfoRepository.getUserName()
    }
}