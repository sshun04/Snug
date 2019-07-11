package com.shojishunsuke.kibunnsns.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PagingRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.CalendarFragmentViewModel
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.android.synthetic.main.activity_calendar.*
import java.util.*

class CalendarActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(createIntent(context))
        }

        private fun createIntent(context: Context): Intent {
            return Intent(context, CalendarActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val viewModel = this.run { ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java) }

        val calender = findViewById<CalendarView>(R.id.calendarView)

        calender.setOnDateChangeListener { calendarView, year, month, day ->
            viewModel.requestPostsByDate(Date(calendarView.date))
        }

        this.datePostsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CalendarActivity, RecyclerView.VERTICAL, false)
        }

        viewModel.postsByDate.observe(this, Observer {
            this.datePostsRecyclerView.adapter =
                PagingRecyclerViewAdapter(this, it as MutableList<Post>, {})
        })
    }
}
