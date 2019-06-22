package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.fragment.PostDialogFragment

class MainActivityViewModel(private val fragmentManager: FragmentManager) : ViewModel() {

    private var postDialog = PostDialogFragment()

    fun setupPostFragment() {
        Log.d("Main", "Tapped!!")
        postDialog = PostDialogFragment()
        postDialog.show(fragmentManager, "MainActivityViewModel")


    }

    fun dismissPostFragment() {
        postDialog.dismiss()
    }
}