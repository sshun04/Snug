package com.shojishunsuke.kibunnsns.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.ChartFragmentViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.viewmodel_factory.ChartFragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_chart.view.*
import java.util.*

class ChartFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: ChartFragmentViewModel
    private lateinit var parentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = inflater.inflate(R.layout.fragment_chart, container, false)
        val lineChart = parentView.lineChart
        val pieChart = parentView.pieChart

        viewModel = requireActivity().run {
            ViewModelProviders.of(this,ChartFragmentViewModelFactory(requireContext())).get(ChartFragmentViewModel::class.java)
        }
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        lineChart.axisLeft.apply {
            valueFormatter = IndexAxisValueFormatter(viewModel.modes)
            axisLineColor = Color.rgb(255, 255, 255)
            gridLineWidth = 1f
            textSize = 12f
            gridColor = R.color.dark_26
            granularity = 1f
            mAxisRange = 4f
            axisMaximum = 4f
            axisMinimum = 0f
        }

        lineChart.axisRight.isEnabled = false
        lineChart.isScaleYEnabled = false

        viewModel.lineEntries.observe(viewLifecycleOwner, Observer {
            lineChart.data = viewModel.getLineChartData()
            lineChart.data.notifyDataChanged()
            lineChart.notifyDataSetChanged()
            lineChart.invalidate()
        })

        viewModel.axisValue.observe(viewLifecycleOwner, Observer {
            lineChart.xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(it)
                granularity = 1f
                mAxisRange = (it.size - 1.0).toFloat()
                axisMaximum = (it.size - 1.0).toFloat()
                axisMinimum = 1f
                axisMinimum = 0f
            }
        })


        viewModel.pieEntries.observe(viewLifecycleOwner, Observer {
            pieChart.data = viewModel.getPieChartData()
            pieChart.setEntryLabelColor(R.color.dark_87)
            pieChart.invalidate()
        })

        viewModel.liveDate.observe(viewLifecycleOwner, Observer {
            parentView.focusedDateTextView.text = it
        })

        parentView.selectDate.apply {
            setOnClickListener(this@ChartFragment)
            isSelected = true
        }

        parentView.selectWeek.setOnClickListener(this)
        parentView.selectMonth.setOnClickListener(this)
        parentView.next.setOnClickListener {
            viewModel.waverRange(it.id)
        }
        parentView.previous.setOnClickListener {
            viewModel.waverRange(it.id)
        }
        return parentView
    }

    override fun onResume() {
        super.onResume()
        switchSelectedBackGround(viewModel.rangeField)
        viewModel.refresh()
    }

    override fun onClick(view: View?) {
        val range = when (view?.id) {
            R.id.selectDate -> Calendar.DATE
            R.id.selectWeek -> Calendar.WEEK_OF_YEAR
            R.id.selectMonth -> Calendar.MONTH
            else -> throw IndexOutOfBoundsException()
        }
        viewModel.onRangeSelected(range)
        switchSelectedBackGround(range)
    }

    private fun switchSelectedBackGround(selectedRange: Int) {
        when (selectedRange) {
            Calendar.DATE -> {
                parentView.selectDate.isSelected = true
                parentView.selectWeek.isSelected = false
                parentView.selectMonth.isSelected = false
            }
            Calendar.WEEK_OF_YEAR -> {
                parentView.selectDate.isSelected = false
                parentView.selectWeek.isSelected = true
                parentView.selectMonth.isSelected = false
            }
            Calendar.MONTH -> {
                parentView.selectDate.isSelected = false
                parentView.selectWeek.isSelected = false
                parentView.selectMonth.isSelected = true
            }
        }
    }





}