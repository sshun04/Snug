package com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.calendar.CalendarFragment
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.chart.ChartFragment
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.my_post.MyPostFragment

class PagerAdapter(fragmentManager: FragmentManager,private val pageTitleList: List<String>) : FragmentPagerAdapter(fragmentManager) {
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
        return pageTitleList[position]
    }

    override fun getCount(): Int = Page.values().size
}