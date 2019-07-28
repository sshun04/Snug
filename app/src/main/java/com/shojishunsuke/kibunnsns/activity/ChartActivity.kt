package com.shojishunsuke.kibunnsns.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.ChartActivityViewModel
import kotlinx.android.synthetic.main.activity_chart.*
import java.util.*

class ChartActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        fun start(context: Context) {
            context.startActivity(createIntent(context))
        }

        private fun createIntent(context: Context): Intent {
            return Intent(context, ChartActivity::class.java)
        }
    }
    private val exData = listOf<Entry>(
    )

    lateinit var viewModel: ChartActivityViewModel

    private val months = listOf(
        "0:00",
        "1:00",
        "2:00",
        "3:00",
        "4:00",
        "5:00",
        "6:00",
        "7:00",
        "8:00",
        "9:00",
        "10:00",
        "11:00",
        "12:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "17:00",
        "18:00",
        "19:00",
        "20:00",
        "21:00",
        "22:00",
        "23:00",
        "24:00"
    )
    private val modes = listOf(
        "\uD83D\uDE00",
//        slightly smiling
        "\uD83D\uDE42",
//        neutral
        "\uD83D\uDE10",
//        slightly frown
        "\uD83D\uDE41",
//        frown
        "☹️"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        viewModel = this.run {
            ViewModelProviders.of(this).get(ChartActivityViewModel::class.java)
        }

        chartToolBar.setNavigationOnClickListener {
            finish()
        }


        lineChart.scrollX = LineChart.SCROLL_AXIS_HORIZONTAL

        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            valueFormatter = IndexAxisValueFormatter(months)
        }


        lineChart.axisLeft.apply {
            valueFormatter = IndexAxisValueFormatter(modes.reversed())
            granularity = 0.4f
            axisMinimum = -1f
            mAxisMaximum = 1f

        }

        lineChart.axisRight.isEnabled = false

        viewModel.entries.observe(this, Observer {
            val lineDataSet1 = LineDataSet(it, "Data Set 1")
                .apply {
                    lineWidth = 1.75f
                    circleRadius = 5f
                    circleHoleRadius =2.5f
                    setDrawValues(false)
                }
            val dataSet = listOf(lineDataSet1)
            val data = LineData(dataSet)
            lineChart.data = data
            lineChart.invalidate()
        })

        viewModel.livedataDate.observe(this, Observer {
            focusedDateTextView.text = it
        })

        selectDate.setOnClickListener(this)
        selectWeek.setOnClickListener(this)
        selectMonth.setOnClickListener(this)
        next.setOnClickListener(this)
        previous.setOnClickListener(this)


    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.selectDate -> {
                viewModel.onRangeSelected(Calendar.DATE)
            }
            R.id.selectWeek -> {
                viewModel.onRangeSelected(Calendar.WEEK_OF_YEAR)
            }
            R.id.selectMonth -> {
                viewModel.onRangeSelected(Calendar.MONTH)
            }
            R.id.next -> {
                viewModel.waverRange(1)
            }
            R.id.previous -> {
                viewModel.waverRange(-1)
            }

        }
    }
}
