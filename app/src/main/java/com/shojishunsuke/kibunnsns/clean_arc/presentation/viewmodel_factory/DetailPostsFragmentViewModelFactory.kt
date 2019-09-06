package com.shojishunsuke.kibunnsns.clean_arc.presentation.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.clean_arc.presentation.DetailPostsFragmentViewModel
import com.shojishunsuke.kibunnsns.model.CloudPost

class DetailPostsFragmentViewModelFactory(private val cloudPost: CloudPost) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailPostsFragmentViewModel(cloudPost) as T
    }
}