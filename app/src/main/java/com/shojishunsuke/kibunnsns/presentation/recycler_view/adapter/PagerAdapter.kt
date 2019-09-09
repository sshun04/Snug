package com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.calendar.CalendarFragment
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.chart.ChartFragment
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.my_post.MyPostFragment

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    enum class Page {
        MyPost,
        Calendar,
        Chart
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            Page.MyPost.ordinal -> MyPostFragment()
            Page.Calendar.ordinal -> CalendarFragment()
            Page.Chart.ordinal -> ChartFragment()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            Page.MyPost.ordinal -> "最近"
            Page.Calendar.ordinal -> "カレンダー"
            Page.Chart.ordinal -> "気分"
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount(): Int = Page.values().size
}