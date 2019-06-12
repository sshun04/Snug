package com.shojishunsuke.kibunnsns.clean_arc.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostDialogViewModel

class PostViewModelFactory(private val onSaveSuccessListener: OnSaveSuccessListener):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostDialogViewModel() as T
    }
}