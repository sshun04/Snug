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
import com.shojishunsuke.kibunnsns.clean_arc.presentation.CalendarFragmentViewModel
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

        calendarToolbar.setNavigationOnClickListener {
            finish()
        }

        val viewModel = this.run { ViewModelProviders.of(this).get(CalendarFragmentViewModel::class.java) }

        calendarView.apply {
            setOnDateChangeListener { _, year, month, date ->
                val dateString = "$year/${month + 1}/$date"
                viewModel.requestPostsByDate(dateString)
                simpleDateTextView.text = "${month + 1}月${date}日"
            }
        }

        val calendar = Calendar.getInstance().apply {
            time = Date()
        }
        simpleDateTextView.text = "${calendar.get(Calendar.MONTH) + 1}月${calendar.get(Calendar.DATE)}日"

        val stringToday =
            "${calendar.get(Calendar.YEAR)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DATE)}"

        viewModel.requestPostsByDate(stringToday)

        focusTodayButton.setOnClickListener {
            calendarView.date = calendar.timeInMillis
            viewModel.requestPostsByDate(stringToday)
            simpleDateTextView.text = "${calendar.get(Calendar.MONTH) + 1}月${calendar.get(Calendar.DATE)}日"
        }

        datePostsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CalendarActivity, RecyclerView.VERTICAL, false)
        }

        viewModel.postsByDate.observe(this, Observer {
            datePostsRecyclerView.adapter =
                PagingRecyclerViewAdapter(this, {}).apply {
                    addNextCollection(it)
                    viewType = 3
                }
        })


    }

}
