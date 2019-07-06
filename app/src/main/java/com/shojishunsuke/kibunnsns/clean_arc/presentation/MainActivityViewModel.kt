package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.data.NaturalLanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.SharedPrefRepository
import com.shojishunsuke.kibunnsns.clean_arc.domain.MainActivityUsecase
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostsSharedUseCase
import com.shojishunsuke.kibunnsns.fragment.PostDialogFragment
import com.shojishunsuke.kibunnsns.model.Post

class MainActivityViewModel(context: Context) : ViewModel() {

    private val useCase:PostsSharedUseCase
    private val _currentPosted = MutableLiveData<Post>()
    private val _postsList = MutableLiveData<List<Post>>()

    init {
        val languageRepository = NaturalLanguageAnalysisRepository(context)
        val dataConfigRepository = SharedPrefRepository(context)
        useCase = PostsSharedUseCase(languageRepository,dataConfigRepository)
        useCase.initialize()
    }

    fun setupPostFragment(fragmentManager: FragmentManager) {
        Log.d("Main", "Tapped!!")
        val postDialog = PostDialogFragment()
        postDialog.showNow(fragmentManager, "MainActivityViewModel")
    }

}