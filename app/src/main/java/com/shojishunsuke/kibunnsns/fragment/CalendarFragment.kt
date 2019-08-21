package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PostRecordRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.CalendarFragmentViewModel
import com.shojishunsuke.kibunnsns.utils.month
import com.shojishunsuke.kibunnsns.utils.year
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import java.util.*

class CalendarFragment : Fragment() {

    lateinit var recyclerViewAdapter:PostRecordRecyclerViewAdapter
    lateinit var viewModel :CalendarFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        viewModel = requireActivity().run { ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java) }


       recyclerViewAdapter = PostRecordRecyclerViewAdapter(requireContext()){
           viewModel.onPostRemoved(it)
       }.apply {
            viewType = 1
        }
        val recyclerView = view.datePostsRecyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            layoutAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.animation_recyclerview)

        }
        view.focusTodayButton.setOnClickListener {
            recyclerViewAdapter.clear()
            viewModel.onFocusToday()
            view.compactCalendar.setCurrentDate(viewModel.getDate())
        }


        view.compactCalendar.apply {
            val currentDate = Calendar.getInstance()
            setCurrentDate(currentDate.time)
            setFirstDayOfWeek(Calendar.SUNDAY)
            setLocale(TimeZone.getDefault(), Locale.JAPAN)
            shouldDrawIndicatorsBelowSelectedDays(true)
            setShouldDrawDaysHeader(true)
            setListener(object : CompactCalendarView.CompactCalendarViewListener {
                override fun onDayClick(dateClicked: Date?) {
                    recyclerViewAdapter.clear()
                    viewModel.setDate(dateClicked?: Date())
                }

                override fun onMonthScroll(firstDayOfNewMonth: Date?) {
                    currentDate.time = firstDayOfNewMonth
                    view.calendarHeaderTextView.text = "${currentDate.year()}年${currentDate.month()}月"
                }
            })
        }
        view.monthNextButton.setOnClickListener {
            view.compactCalendar.scrollLeft()
        }
        view.monthPreviousButton.setOnClickListener {
            view.compactCalendar.scrollRight()
        }

        viewModel.eventDateList.observe(viewLifecycleOwner, Observer {
            view.compactCalendar.removeAllEvents()
            view.compactCalendar.addEvents(it)
        })

        viewModel.dateText.observe(viewLifecycleOwner, Observer {
            view.simpleDateTextView.text = it
        })

        viewModel.postsOfDate.observe(viewLifecycleOwner, Observer {
            recyclerViewAdapter.addNextCollection(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })

        view.detailSwitch.setOnCheckedChangeListener { _, isDetail ->
            recyclerViewAdapter.viewType = if (isDetail) 2 else 1
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        recyclerViewAdapter.clear()
        viewModel.refresh()
    }
}