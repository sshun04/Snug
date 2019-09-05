package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.EntryXComparator
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.domain.ChartFragmentUsecase
import com.shojishunsuke.kibunnsns.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ChartFragmentViewModel(context:Context) : ViewModel() {

    var rangeField = Calendar.DATE
    private val date: Calendar = Calendar.getInstance()

    private val pieColorsMap = mapOf(
        "Positive" to context.resources.getColor(R.color.color_positive),
        "Neutral" to context.resources.getColor(R.color.color_neutral),
        "Negative" to context.resources.getColor(R.color.color_negative)
    )
    private val daysOfWeek = listOf("月", "火", "水", "木", "金", "土", "日")
    private val hours: List<String> = (0..24).map { "$it:00" }

    val modes = listOf(
        "☹️",
        "\uD83D\uDE41",
        "\uD83D\uDE10",
        "\uD83D\uDE42",
        "\uD83D\uDE00"
    )

    private val _lineEntries = MutableLiveData<List<Entry>>()
    val lineEntries: LiveData<List<Entry>> get() = _lineEntries

    private val _pieEntries = MutableLiveData<List<PieEntry>>()
    val pieEntries: LiveData<List<PieEntry>> get() = _pieEntries

    private val _liveDate = MutableLiveData<String>()
    val liveDate: LiveData<String> get() = _liveDate

    private val _axisValue = MutableLiveData<List<String>>()
    val axisValue: LiveData<List<String>> get() = _axisValue


    private val usecase = ChartFragmentUsecase()


    fun onRangeSelected(field: Int) {
        this.rangeField = field
        requestData()
    }

    fun waverRange(viewId: Int) {
        if (rangeField == Calendar.WEEK_OF_YEAR) {
            val value = if (viewId == R.id.next) 6 else -6
            date.add(Calendar.DAY_OF_MONTH, value)
        } else {
            val value = if (viewId == R.id.next) 1 else -1
            date.add(rangeField, value)
        }
        requestData()
    }

    fun refresh(){
        requestData()
    }

    private fun requestData() {
        upDateAxisValue()
        when (rangeField) {
            Calendar.DATE -> {
                _liveDate.postValue("${date.year()}/${date.month()}/${date.dayOfMonth()}")
                requestDataOfDate()
            }
            Calendar.WEEK_OF_YEAR -> {
                date.add(Calendar.DAY_OF_MONTH, date.diffToMonday())
                val lastDayOfWeek = date.clone() as Calendar
                lastDayOfWeek.apply {
                    add(Calendar.DAY_OF_MONTH, 6)
                }
                _liveDate.postValue("${date.month()}/${date.dayOfMonth()} - ${lastDayOfWeek.month()}/${lastDayOfWeek.dayOfMonth()}")
                requestDataOfWeek(date, lastDayOfWeek)
            }
            Calendar.MONTH -> {
                _liveDate.postValue("${date.year()}/${date.month()}")
                requestDataOfMonth()
            }
        }
    }

    private fun requestDataOfDate() {

        GlobalScope.launch {
            val chartDataPair = usecase.getDataOfDate(date)
            val lineData = chartDataPair.first
            val pieData = chartDataPair.second
            launch(Dispatchers.IO) {
                _lineEntries.postValue(lineData)
                _pieEntries.postValue(pieData)
            }
        }
    }

    private fun requestDataOfWeek(firstDayOfWeek: Calendar, lastDayOfWeek: Calendar) {
        GlobalScope.launch {
            val chartDataPair = usecase.getDataOfWeek(firstDayOfWeek, lastDayOfWeek)
            val lineData = chartDataPair.first
            val pieData = chartDataPair.second
            launch(Dispatchers.IO) {
                _lineEntries.postValue(lineData)
                _pieEntries.postValue(pieData)
            }
        }
    }

    private fun requestDataOfMonth() {
        GlobalScope.launch {
            val chartDataPair = usecase.getDataOfMonth(date)
            val lineData = chartDataPair.first
            val pieData = chartDataPair.second
            launch(Dispatchers.IO) {
                _lineEntries.postValue(lineData)
                _pieEntries.postValue(pieData)
            }
        }
    }

    fun getPieChartData(): PieData {
        val pieDataSet = PieDataSet(_pieEntries.value, "").apply {
            sliceSpace = 2f
            setDrawValues(false)
            colors = getPieChartColorList()
        }
        val pieData = PieData(pieDataSet).apply {
            setValueFormatter(PercentFormatter())
            setValueTextColor(Color.BLACK)
            setDrawValues(false)
        }
        return pieData
    }

    fun getLineChartData(): LineData {
        Collections.sort(_lineEntries.value, EntryXComparator())
        val lineDataSet = LineDataSet(_lineEntries.value, "投稿")
            .apply {
                lineWidth = 2.5f
                circleRadius = 5f
                setDrawValues(false)
            }
        return LineData(lineDataSet)
    }

    private fun getPieChartColorList(): List<Int> {
        val colorList = mutableListOf<Int>()
        _pieEntries.value?.forEach {
            val color = pieColorsMap[it.label] ?: Color.rgb(169, 255, 242)
            colorList.add(color)
        }
        return colorList
    }

    private fun upDateAxisValue() {
        when (rangeField) {
            Calendar.DATE -> {
                _axisValue.postValue(hours)
            }
            Calendar.WEEK_OF_YEAR -> {
                _axisValue.postValue(daysOfWeek)
            }
            Calendar.MONTH -> {
                val list = (1..date.monthDays()).map { "${date.month()}/$it" }
                _axisValue.postValue(list)
            }
        }
    }

}