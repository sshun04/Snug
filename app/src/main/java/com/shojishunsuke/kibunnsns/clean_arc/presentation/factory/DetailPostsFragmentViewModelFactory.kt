package com.shojishunsuke.kibunnsns.clean_arc.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.clean_arc.presentation.DetailPostsFragmentViewModel
import com.shojishunsuke.kibunnsns.model.Post

class DetailPostsFragmentViewModelFactory(private val post: Post) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailPostsFragmentViewModel(post) as T
    }
}