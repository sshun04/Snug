package com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shojishunsuke.kibunnsns.presentation.secen.main.tutorial.pages.TutorialFirstFragment
import com.shojishunsuke.kibunnsns.presentation.secen.main.tutorial.pages.TutorialSecondFragment
import com.shojishunsuke.kibunnsns.presentation.secen.main.tutorial.pages.TutorialThirdFragment

class TutorialPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
   enum class TutorialPage(val value: Int) {
        First(0),
        Second(1),
        Last(2),
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            TutorialPage.First.value -> TutorialFirstFragment()
            TutorialPage.Second.value -> TutorialSecondFragment()
            TutorialPage.Last.value -> TutorialThirdFragment()
            else -> TutorialFirstFragment()
        }
    }

    override fun getCount(): Int = TutorialPage.values().size
}