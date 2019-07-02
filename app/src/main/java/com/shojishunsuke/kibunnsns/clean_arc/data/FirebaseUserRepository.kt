package com.shojishunsuke.kibunnsns.clean_arc.data

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.AuthRepository
import java.lang.IllegalArgumentException

class FirebaseUserRepository:AuthRepository {
    private val user = FirebaseAuth.getInstance().currentUser
    private val TAG = "FirebaseUser"

    override fun getUserName(): String {
        return user?.displayName?:"匿名"
    }

    override fun updateUserName(userName: String) {
        val profileUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(userName)
            .build()
        user?.updateProfile(profileUpdate)
            ?.addOnCompleteListener {task->
                if (task.isSuccessful){
                    Log.d(TAG,"User profile name updated.")
                }else{
                    Log.w(TAG,"Failure User profile name updating.")
                }

            }
    }

    override fun getUserPhotoUri(): Uri? {
//        TODO Uriが見つからなかった場合デフォルトのUriを返すようにする
        return user?.photoUrl
    }

    override fun updateUserPhoto(uri: Uri) {
        val profileUpdate = UserProfileChangeRequest.Builder()
            .setPhotoUri(uri)
            .build()

        user?.updateProfile(profileUpdate)
            ?.addOnCompleteListener {
                    task->
                if (task.isSuccessful){
                    Log.d(TAG,"User profile photo updated.")
                }else{
                    Log.w(TAG,"Failure User profile photo updating.")
                }
            }
    }


}