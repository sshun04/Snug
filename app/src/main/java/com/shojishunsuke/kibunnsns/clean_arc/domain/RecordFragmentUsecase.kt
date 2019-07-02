package com.shojishunsuke.kibunnsns.clean_arc.domain

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.shojishunsuke.kibunnsns.clean_arc.data.CloudStorageRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordFragmentUsecase(uploadListener: CloudStorageRepository.ImageUploadListener) {
    //    private val roomRepository = RoomDatabaseRepository()
    private val userInfoRepository = FirebaseUserRepository()
    private val cloutStorageRepository = CloudStorageRepository(uploadListener)

    //    suspend fun loadCollcetion(): List<LocalPost> {
////        return roomRepository.loadWholeCollection()
//    }
    fun saveUserName(name: String) {
        GlobalScope.launch {
            //            roomRepository.editUserName(name)
        }
        userInfoRepository.updateUserName(name)
    }

    fun saveIconToCloud(bitmap: Bitmap) {
        GlobalScope.launch {
            cloutStorageRepository.uploadImage(bitmap)
        }
    }

    fun saveLocalPhotoUri(uri: Uri) {
        userInfoRepository.updateUserPhoto(uri)
    }

    fun getIconStorageRef(): StorageReference {
        val uri = userInfoRepository.getUserPhotoUri()
            ?: Uri.parse("https://firebasestorage.googleapis.com/v0/b/firestore-tutorial-ff769.appspot.com/o/icons%2Fe2289e48-7128-435e-81f9-7d3c1cead54d.png?alt=media&token=a2b357a3-b31e-4bae-9aa9-d430510f860c")
        return cloutStorageRepository.getStorageRefByUri(uri)
    }


    fun getUserName(): String {
        return userInfoRepository.getUserName()
    }


}