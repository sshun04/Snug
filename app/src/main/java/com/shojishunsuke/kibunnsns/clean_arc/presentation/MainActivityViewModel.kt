package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.fragment.HomePostsFragment
import com.shojishunsuke.kibunnsns.fragment.PostDialogFragment
import com.shojishunsuke.kibunnsns.fragment.RecordFragment

class MainActivityViewModel(private val fragmentManager: FragmentManager) : ViewModel() {

    fun setupPostFragment() {
        Log.d("Main", "Tapped!!")
        val postDialog = PostDialogFragment()
        postDialog.show(fragmentManager, "MainActivityViewModel")
    }
}