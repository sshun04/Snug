package com.shojishunsuke.kibunnsns.presentation.secen.main

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.data.repository.impl.SharedPrefRepository
import com.shojishunsuke.kibunnsns.domain.use_case.MainActivityUseCase
import com.shojishunsuke.kibunnsns.presentation.secen.main.post_dialog.PostDialogFragment

class MainActivityViewModel(application: Application) : ViewModel() {
    var isNavigationInitialized: Boolean = false
    private val useCase: MainActivityUseCase

    init {
        val dataConfigRepository = SharedPrefRepository(application)
        useCase = MainActivityUseCase(dataConfigRepository)
    }

    fun isInitialized():Boolean = useCase.isInitialized

    fun setupPostFragment(fragmentManager: FragmentManager) {
        val postDialog = PostDialogFragment()
        postDialog.show(fragmentManager.beginTransaction(), "fragment")
    }

    fun onAuthSuccess() {
        useCase.updateUser()
    }

    class MainActivityViewModelFactory(private val application: Application) :
        ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(application) as T
        }
    }
}