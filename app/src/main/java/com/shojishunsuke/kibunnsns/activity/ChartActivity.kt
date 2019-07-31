package com.shojishunsuke.kibunnsns.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
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

    private val days = listOf("日", "月", "火", "水", "木", "金", "土")

    private lateinit var viewModel: ChartActivityViewModel
    private val hours = listOf(
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
        lineChart.scrollY = LineChart.SCROLL_AXIS_VERTICAL

        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        lineChart.axisLeft.apply {
            valueFormatter = IndexAxisValueFormatter(modes.reversed())
            axisLineColor = Color.rgb(255, 255, 255)
            gridLineWidth = 1f
            gridColor = R.color.dark_26
            granularity = 1f
            mAxisRange = 4f
            axisMaximum = 4f
            axisMinimum = 0f
        }

        lineChart.axisRight.isEnabled = false

        viewModel.lineEntries.observe(this, Observer {
            val lineDataSet = LineDataSet(it, "投稿")
                .apply {
                    lineWidth = 2.5f
                    circleRadius = 5f
                    setDrawValues(false)
                }

            val data = LineData(lineDataSet)
            lineChart.data = data
            lineChart.data.notifyDataChanged()
            lineChart.notifyDataSetChanged()
            lineChart.invalidate()
        })

        viewModel.pieEntries.observe(this, Observer {
            val pieDataSet = PieDataSet(it, "").apply {
                setDrawValues(true)
                colors = listOf(Color.rgb(250, 210, 218), Color.rgb(169, 255, 242), Color.rgb(170, 240, 255))
            }
            val pieData = PieData(pieDataSet).apply {
                setValueFormatter(PercentFormatter())
                setValueTextColor(resources.getColor(R.color.dark_26))
            }

            pieChart.data = pieData
            pieChart.invalidate()
        })

        viewModel.liveDate.observe(this, Observer {
            focusedDateTextView.text = it
        })

        setupDateAxis()

        selectDate.apply {
            setOnClickListener(this@ChartActivity)
            isSelected = true
        }
        selectWeek.setOnClickListener(this)
        selectMonth.setOnClickListener(this)
        next.setOnClickListener(this)
        previous.setOnClickListener(this)


    }


    private fun setupDateAxis() {
        lineChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(hours)
            granularity = 1f
            mAxisRange = 24f
            axisMaximum = 24f
            axisMinimum = 0f

        }

    }

    private fun setupWeekAxis() {
        lineChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(days)
        }
    }

    private fun setupMonthAxis() {
        lineChart.xAxis.apply {

        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onRangeSelected(Calendar.DATE)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.selectDate -> {
                viewModel.onRangeSelected(Calendar.DATE)
                setupDateAxis()
                selectDate.isSelected = true
                selectWeek.isSelected = false
                selectMonth.isSelected = false
            }
            R.id.selectWeek -> {
                viewModel.onRangeSelected(Calendar.WEEK_OF_YEAR)
                setupWeekAxis()
                selectDate.isSelected = false
                selectWeek.isSelected = true
                selectMonth.isSelected = false
            }
            R.id.selectMonth -> {
                viewModel.onRangeSelected(Calendar.MONTH)
                setupMonthAxis()
                selectDate.isSelected = false
                selectWeek.isSelected = false
                selectMonth.isSelected = true
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
