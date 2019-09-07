package com.shojishunsuke.kibunnsns.clean_arc.data

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.AuthRepository

class FirebaseUserRepository : AuthRepository {
    private var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    companion object {
        const val TAG: String = "FirebaseUser"
    }

    override fun updateUser() {
        this.user = FirebaseAuth.getInstance().currentUser
    }

    override fun getUserName(): String {
        val savedName = user?.displayName ?: ""
        return if (savedName.isNotBlank()) savedName else "匿名"
    }


    override fun updateUserName(userName: String) {
        val profileUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build()
        user?.updateProfile(profileUpdate)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User profile name updated.")
                    } else {
                        Log.w(TAG, "Failure User profile name updating.")
                    }
                }
    }

    override fun getUserId(): String {
        return user?.uid ?: ""
    }

    override fun getUserPhotoUri(): Uri {
        return user?.photoUrl
                ?: Uri.parse("https://firebasestorage.googleapis.com/v0/b/firestore-tutorial-ff769.appspot.com/o/icons%2Fe2289e48-7128-435e-81f9-7d3c1cead54d.png?alt=media&token=a2b357a3-b31e-4bae-9aa9-d430510f860c")
    }

    override suspend fun updateUserPhoto(uri: Uri) {
        val profileUpdate = UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()

        user?.updateProfile(profileUpdate)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User profile photo updated.")
                    } else {
                        Log.w(TAG, "Failure User profile photo updating.")
                    }
                }
    }
}