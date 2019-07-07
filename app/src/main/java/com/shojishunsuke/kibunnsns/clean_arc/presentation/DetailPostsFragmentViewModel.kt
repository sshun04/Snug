package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.shojishunsuke.kibunnsns.clean_arc.domain.DetailPostsFragmentUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPostsFragmentViewModel : ViewModel() {
    private val useCase = DetailPostsFragmentUsecase()

    fun requestPagingOptionBuilder(lifecycleOwner:LifecycleOwner):FirestorePagingOptions<Post>{
        return useCase.getPagingOptionBuilder(lifecycleOwner)
    }
}