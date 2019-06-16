package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.fragment.HomePostsFragment
import com.shojishunsuke.kibunnsns.fragment.PostDialogFragment
import com.shojishunsuke.kibunnsns.fragment.RecordFragment

class MainActivityViewModel(private val fragmentManager: FragmentManager) : ViewModel() {
//
//    private val homeFragment:HomePostsFragment
//    private val recordFragment:RecordFragment

    init {
//        homeFragment = HomePostsFragment()
//        recordFragment = RecordFragment()


    }

    fun setupPostFragment() {
        Log.d("Main", "Tapped!!")
        val postDialog = PostDialogFragment()
        postDialog.show(fragmentManager, "MainActivityViewModel")
    }

//    fun onSwitchFragment(position: Int) {
//        if (position == 1) {
//            fragmentManager.beginTransaction().replace(R.id.mainBackGround,homeFragment).commit()
//
//        } else {
//            fragmentManager.beginTransaction().replace(R.id.mainBackGround,recordFragment).commit()
//        }
//    }
}