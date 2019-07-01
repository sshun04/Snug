package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import android.net.Uri

interface AuthRepository {
    fun updateUserName(userName:String)
    fun updateUserPhoto(uri: Uri)
    fun getUserName():String
    fun getUserPhotoUri():Uri
}