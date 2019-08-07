package com.shojishunsuke.kibunnsns.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PagingRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.CalendarActivityViewModel
import kotlinx.android.synthetic.main.fragment_calendar.*

//class CalendarActivity : AppCompatActivity() {
//
//    companion object {
//        fun start(context: Context) {
//            context.startActivity(createIntent(context))
//        }
//
//        private fun createIntent(context: Context): Intent {
//            return Intent(context, CalendarActivity::class.java)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_calendar)
//
//        val viewModel = this.run { ViewModelProviders.of(this).get(CalendarActivityViewModel::class.java) }
//
//        calendarView.apply {
//            setOnDateChangeListener { _, year, month, date ->
//                viewModel.setDate(year, month, date)
//            }
//        }
//
//        focusTodayButton.setOnClickListener {
//            viewModel.onFocusToday()
//            calendarView.date = viewModel.getDateInLong()
//        }
//
//        datePostsRecyclerView.apply {
//            layoutManager = LinearLayoutManager(this@CalendarActivity, RecyclerView.VERTICAL, false)
//        }
//
//        viewModel.dateText.observe(this, Observer {
//            simpleDateTextView.text = it
//        })
//        viewModel.postsOfDate.observe(this, Observer {
//            datePostsRecyclerView.adapter =
//                PagingRecyclerViewAdapter(this, {}).apply {
//                    addNextCollection(it)
//                    viewType = 3
//                }
//        })
//    }
//
//}
