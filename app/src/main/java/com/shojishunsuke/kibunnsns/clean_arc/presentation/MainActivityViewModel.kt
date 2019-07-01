package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.data.SharedPrefRepository
import com.shojishunsuke.kibunnsns.clean_arc.domain.MainActivityUsecase
import com.shojishunsuke.kibunnsns.fragment.PostDialogFragment

class MainActivityViewModel(context: Context) : ViewModel() {

    private val useCase = MainActivityUsecase(SharedPrefRepository(context))

    init {
        useCase.initialize()
    }

    fun setupPostFragment(fragmentManager: FragmentManager) {
        Log.d("Main", "Tapped!!")
        val postDialog = PostDialogFragment()
        postDialog.showNow(fragmentManager, "MainActivityViewModel")
    }

}