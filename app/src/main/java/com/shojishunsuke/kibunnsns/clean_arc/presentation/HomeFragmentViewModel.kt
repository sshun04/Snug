package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.storage.StorageReference
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.domain.HomePostsFragmentUseCase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragmentViewModel:ViewModel() {
    private val useCase = HomePostsFragmentUseCase()

    fun requestPagingOptionBuilder(lifecycleOwner:LifecycleOwner):FirestorePagingOptions<Post>{
        return useCase.getPagingOptionBuilder(lifecycleOwner)
    }
}