package com.shojishunsuke.kibunnsns.presentation.secen.main

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.data.repository.impl.SharedPrefRepository
import com.shojishunsuke.kibunnsns.domain.use_case.MainActivityUseCase
import com.shojishunsuke.kibunnsns.presentation.secen.main.post_dialog.PostDialogFragment

class MainActivityViewModel(context: Context) : ViewModel() {
    var isNavigationInitialized: Boolean = false
    private val useCase: MainActivityUseCase

    init {
        val dataConfigRepository = SharedPrefRepository(context)
        useCase = MainActivityUseCase(dataConfigRepository)
        useCase.initialize()
    }

    fun setupPostFragment(fragmentManager: FragmentManager) {
        Log.d("Main", "Tapped!!")
        val postDialog = PostDialogFragment()
        postDialog.show(fragmentManager.beginTransaction(), "a")
    }

    fun onAuthSuccess() {
        useCase.updateUser()
    }

    class MainActivityViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(context) as T
        }
    }
}