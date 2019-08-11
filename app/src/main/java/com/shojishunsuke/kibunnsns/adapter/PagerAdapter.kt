package com.shojishunsuke.kibunnsns.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shojishunsuke.kibunnsns.fragment.CalendarFragment
import com.shojishunsuke.kibunnsns.fragment.ChartFragment
import com.shojishunsuke.kibunnsns.fragment.PostsRecordFragment

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PostsRecordFragment()
            1 -> CalendarFragment()
            2 -> ChartFragment()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "最近"
            1 -> "カレンダー"
            2 -> "気分"
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount(): Int = 3
}