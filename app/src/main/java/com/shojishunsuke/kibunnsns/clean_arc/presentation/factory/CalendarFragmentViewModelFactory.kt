package com.shojishunsuke.kibunnsns.clean_arc.presentation.factory

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.clean_arc.presentation.CalendarFragmentViewModel

class CalendarFragmentViewModelFactory(private val viewLifecycleOwner: LifecycleOwner) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalendarFragmentViewModel(viewLifecycleOwner) as T
    }
}
