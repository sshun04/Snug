package com.shojishunsuke.kibunnsns.clean_arc.presentation.factory

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.clean_arc.presentation.MainActivityViewModel

class MainActivityViewModelFactory(private val fragmentManager: FragmentManager) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(fragmentManager) as T
    }
}