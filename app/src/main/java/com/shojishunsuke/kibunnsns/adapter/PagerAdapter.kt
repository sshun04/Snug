package com.shojishunsuke.kibunnsns.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shojishunsuke.kibunnsns.fragment.CalendarFragment
import com.shojishunsuke.kibunnsns.fragment.ChartFragment

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CalendarFragment()
            1 -> ChartFragment()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "カレンダー"
            1 -> "気分"
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount(): Int = 2
}