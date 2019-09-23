package com.shojishunsuke.kibunnsns.presentation.secen.tutorial.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shojishunsuke.kibunnsns.presentation.secen.tutorial.page.TutorialFirstFragment

class TutorialPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private enum class Page(val value: Int) {
        First(0),
        Second(1),
        Last(2)
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            Page.First.value -> TutorialFirstFragment()
            Page.Second.value -> TutorialFirstFragment()
            Page.Last.value -> TutorialFirstFragment()
            else -> TutorialFirstFragment()
        }

    }

    override fun getCount(): Int = Page.values().size

}