package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.fragment.PostDialogFragment

class MainActivityViewModel : ViewModel() {

    private var postDialog = PostDialogFragment()

    fun setupPostFragment(fragmentManager: FragmentManager) {
        Log.d("Main", "Tapped!!")
        postDialog = PostDialogFragment()
        postDialog.showNow(fragmentManager, "MainActivityViewModel")


    }

    fun dismissPostFragment() {
        postDialog.dismiss()
    }
}