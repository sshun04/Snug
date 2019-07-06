package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.data.NaturalLanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.SharedPrefRepository
import com.shojishunsuke.kibunnsns.clean_arc.domain.MainActivityUsecase
import com.shojishunsuke.kibunnsns.fragment.PostDialogFragment
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(context: Context) : ViewModel() {

    private val useCase:MainActivityUsecase
    private val _postsList = MutableLiveData<List<Post>>()

    init {
        val dataConfigRepository = SharedPrefRepository(context)
        useCase = MainActivityUsecase(dataConfigRepository)
        useCase.initialize()


    }

    fun setupPostFragment(fragmentManager: FragmentManager) {
        Log.d("Main", "Tapped!!")
        val postDialog = PostDialogFragment()
        postDialog.showNow(fragmentManager, "MainActivityViewModel")
    }

}