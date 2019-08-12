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
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PostRecordRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.CalendarFragmentViewModel
import kotlinx.android.synthetic.main.fragment_calendar.view.*

class CalendarFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        val viewModel = requireActivity().run { ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java) }


        val recyclerViewAdapter = PostRecordRecyclerViewAdapter(requireContext()).apply {
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
            view.calendarView.date = viewModel.getDateInLong()
        }

        view.calendarView.apply {
            setOnDateChangeListener { _, year, month, date ->
                recyclerViewAdapter.clear()
                viewModel.setDate(year, month, date)
            }
        }

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
}