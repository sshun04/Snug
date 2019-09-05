package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shojishunsuke.kibunnsns.clean_arc.data.SharedPrefRepository
import com.shojishunsuke.kibunnsns.clean_arc.domain.MainActivityUsecase
import com.shojishunsuke.kibunnsns.fragment.PostDialogFragment

class MainActivityViewModel(context: Context) : ViewModel() {

    private val useCase:MainActivityUsecase
    var isNavigationInitialized:Boolean = false

    init {
        val dataConfigRepository = SharedPrefRepository(context)
        useCase = MainActivityUsecase(dataConfigRepository)
        useCase.initialize()
    }

    fun setupPostFragment(fragmentManager: FragmentManager) {
        Log.d("Main", "Tapped!!")
        val postDialog = PostDialogFragment()
        postDialog.show(fragmentManager.beginTransaction(),"a")
    }

    fun onAuthSuccess(){
        useCase.updateUser()
    }

}