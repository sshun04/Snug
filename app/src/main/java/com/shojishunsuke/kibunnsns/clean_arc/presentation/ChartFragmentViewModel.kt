package com.shojishunsuke.kibunnsns.clean_arc.presentation

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

class ChartFragmentViewModel : ViewModel() {

    var rangeField = Calendar.DATE
    private val date: Calendar = Calendar.getInstance()

    private val pieColorsMap = mapOf(
        "Positive" to Color.rgb(250, 210, 218),
        "Neutral" to Color.rgb(169, 255, 242),
        "Negative" to Color.rgb(170, 240, 255)
    )
    private val daysOfWeek = listOf("月", "火", "水", "木", "金", "土", "日")
    private val hours: MutableList<String> = mutableListOf()

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

    init {
        for (i in 0..24) {
            hours.add("$i:00")
        }
        requestData()
        _liveDate.postValue("${date.year()}/${date.month()}/${date.dayOfMonth()}")
    }

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
            setDrawValues(true)
            colors = getPieChartColorList()
        }
        val pieData = PieData(pieDataSet).apply {
            setValueFormatter(PercentFormatter())
            setValueTextColor(Color.BLACK)
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
                val list = mutableListOf<String>()
                for (i in 1..date.monthDays()) {
                    list.add("${date.month()}/$i")
                }
                _axisValue.postValue(list)
            }
        }
    }


}