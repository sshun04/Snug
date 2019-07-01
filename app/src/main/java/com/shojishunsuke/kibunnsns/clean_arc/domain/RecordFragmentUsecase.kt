package com.shojishunsuke.kibunnsns.clean_arc.domain

import android.net.Uri
import androidx.fragment.app.FragmentManager
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.RoomDatabaseRepository
import com.shojishunsuke.kibunnsns.model.LocalPost
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordFragmentUsecase {
//    private val roomRepository = RoomDatabaseRepository()
    private val userInfoRepository = FirebaseUserRepository()

//    suspend fun loadCollcetion(): List<LocalPost> {
////        return roomRepository.loadWholeCollection()
//    }
    fun saveUserName(name: String) {
        GlobalScope.launch {
//            roomRepository.editUserName(name)
        }
        userInfoRepository.updateUserName(name)
    }

    fun savePhotoUri(uri: Uri){
        userInfoRepository.updateUserPhoto(uri)
    }

    fun getUserPhotoUri():Uri {
        return userInfoRepository.getUserPhotoUri()
    }
    fun getUserName(): String {
        return userInfoRepository.getUserName()
    }


}