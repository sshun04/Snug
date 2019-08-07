package com.shojishunsuke.kibunnsns.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shojishunsuke.kibunnsns.fragment.CalendarFargment
import com.shojishunsuke.kibunnsns.fragment.ChartFragment

class PagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> CalendarFargment()
            1->ChartFragment()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount(): Int = 2
}