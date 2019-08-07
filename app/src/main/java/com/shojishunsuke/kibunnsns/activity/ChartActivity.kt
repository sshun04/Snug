package com.shojishunsuke.kibunnsns.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.ChartActivityViewModel
import kotlinx.android.synthetic.main.fragment_chart.*
import java.util.*
//
//class ChartActivity : AppCompatActivity(), View.OnClickListener {
//
//    companion object {
//        fun start(context: Context) {
//            context.startActivity(createIntent(context))
//        }
//
//        private fun createIntent(context: Context): Intent {
//            return Intent(context, ChartActivity::class.java)
//        }
//    }
//
//    private lateinit var viewModel: ChartActivityViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chart)
//
//        viewModel = this.run {
//            ViewModelProviders.of(this).get(ChartActivityViewModel::class.java)
//        }
//        chartToolBar.setNavigationOnClickListener {
//            finish()
//        }
//        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
//
//        lineChart.axisLeft.apply {
//            valueFormatter = IndexAxisValueFormatter(viewModel.modes)
//            axisLineColor = Color.rgb(255, 255, 255)
//            gridLineWidth = 1f
//            textSize = 12f
//            gridColor = R.color.dark_26
//            granularity = 1f
//            mAxisRange = 4f
//            axisMaximum = 4f
//            axisMinimum = 0f
//        }
//
//        lineChart.axisRight.isEnabled = false
//
//        viewModel.lineEntries.observe(this, Observer {
//            lineChart.data = viewModel.getLineChartData()
//            lineChart.data.notifyDataChanged()
//            lineChart.notifyDataSetChanged()
//            lineChart.invalidate()
//        })
//
//        viewModel.axisValue.observe(this, Observer {
//            lineChart.xAxis.apply {
//                valueFormatter = IndexAxisValueFormatter(it)
//                granularity = 1f
//                mAxisRange = it.size.toFloat()
//                axisMaximum = it.size.toFloat()
//                axisMinimum = 1f
//                axisMinimum = 0f
//            }
//        })
//
//        viewModel.pieEntries.observe(this, Observer {
//            pieChart.data = viewModel.getPieChartData()
//            pieChart.setEntryLabelColor(R.color.dark_87)
//            pieChart.invalidate()
//        })
//
//        viewModel.liveDate.observe(this, Observer {
//            focusedDateTextView.text = it
//        })
//
//        selectDate.apply {
//            setOnClickListener(this@ChartActivity)
//            isSelected = true
//        }
//
//        selectWeek.setOnClickListener(this)
//        selectMonth.setOnClickListener(this)
//        next.setOnClickListener {
//            viewModel.waverRange(it.id)
//        }
//        previous.setOnClickListener {
//            viewModel.waverRange(it.id)
//        }
//    }
//
////    private fun setupDateAxis() {
////        lineChart.setVisibleXRangeMaximum(8f)
////        lineChart.xAxis.apply {
////            valueFormatter = IndexAxisValueFormatter(viewModel.hours)
////            granularity = 1f
////            mAxisRange = 10f
////            axisMaximum = 24f
////            axisMinimum = 0f
////        }
////    }
////
////    private fun setupWeekAxis() {
////        lineChart.xAxis.apply {
////            valueFormatter = IndexAxisValueFormatter(viewModel.weekOfDays)
////        }
////    }
////
////    private fun setupMonthAxis() {
////        lineChart.xAxis.apply {
////            val axisValue = viewModel.getDaysOfMonth()
////            valueFormatter = IndexAxisValueFormatter(axisValue)
////            granularity = 1f
////            mAxisRange = axisValue.size.toFloat()
////            axisMaximum = axisValue.size.toFloat()
////            axisMinimum = 1f
////        }
////    }
//
//    override fun onStart() {
//        super.onStart()
//
//    }
//
//    override fun onClick(view: View?) {
//        when (view?.id) {
//            R.id.selectDate -> {
//                viewModel.onRangeSelected(Calendar.DATE)
//                selectDate.isSelected = true
//                selectWeek.isSelected = false
//                selectMonth.isSelected = false
//            }
//            R.id.selectWeek -> {
//                viewModel.onRangeSelected(Calendar.WEEK_OF_YEAR)
//                selectDate.isSelected = false
//                selectWeek.isSelected = true
//                selectMonth.isSelected = false
//            }
//            R.id.selectMonth -> {
//                viewModel.onRangeSelected(Calendar.MONTH)
//                selectDate.isSelected = false
//                selectWeek.isSelected = false
//                selectMonth.isSelected = true
//            }
//        }
//    }
//}
