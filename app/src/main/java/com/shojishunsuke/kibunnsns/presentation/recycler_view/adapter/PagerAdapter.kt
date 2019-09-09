package com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.calendar.CalendarFragment
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.chart.ChartFragment
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.my_post.MyPostFragment

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    enum class Page(val value:Int) {
        MyPost(0),
        Calendar(1),
        Chart(2)
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            Page.MyPost.value -> MyPostFragment()
            Page.Calendar.value -> CalendarFragment()
            Page.Chart.value -> ChartFragment()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            Page.MyPost.value -> "最近"
            Page.Calendar.value -> "カレンダー"
            Page.Chart.value -> "気分"
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount(): Int = Page.values().size
}