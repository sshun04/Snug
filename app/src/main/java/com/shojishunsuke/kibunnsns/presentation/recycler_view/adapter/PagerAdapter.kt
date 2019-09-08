package com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.calendar.CalendarFragment
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.chart.ChartFragment
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.my_post.MyPostFragment

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyPostFragment()
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