package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class AuthSharedViewModel:ViewModel() {
    val cuurentUser : MutableLiveData<FirebaseUser> = MutableLiveData()


}