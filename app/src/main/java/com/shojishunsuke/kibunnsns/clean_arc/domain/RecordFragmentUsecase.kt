package com.shojishunsuke.kibunnsns.clean_arc.domain

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.shojishunsuke.kibunnsns.clean_arc.data.CloudStorageRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordFragmentUsecase(uploadListener: CloudStorageRepository.ImageUploadListener) {
    private val userInfoRepository = FirebaseUserRepository()
    private val cloutStorageRepository = CloudStorageRepository(uploadListener)

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