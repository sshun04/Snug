package com.shojishunsuke.kibunnsns.clean_arc.presentation.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel

class SharedViewModelFactory(private val context: Context):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostsSharedViewModel(context) as T
    }
}