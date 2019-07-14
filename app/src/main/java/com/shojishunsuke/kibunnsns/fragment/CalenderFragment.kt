package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PagingRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.CalendarFragmentViewModel
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import java.util.*

class CalenderFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        val viewModel = this.run { ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java) }

        val calender = view.findViewById<CalendarView>(R.id.calendarView)

        calender.setOnDateChangeListener { calendarView, year, month, day ->
            viewModel.requestPostsByDate("")
        }

        view.datePostsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        viewModel.postsByDate.observe(this, androidx.lifecycle.Observer {
            view.datePostsRecyclerView.adapter =
                PagingRecyclerViewAdapter(requireContext(), {}).apply {
                    addNextCollection(it)
                }
        })


        return view
    }
}