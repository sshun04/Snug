package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.EntryXComparator
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.domain.ChartActivityUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ChartActivityViewModel : ViewModel() {

    private var rangeField = Calendar.DATE
    private val date: Calendar = Calendar.getInstance()

    private val pieColorsMap = mapOf(
        "Positive" to Color.rgb(250, 210, 218),
        "Neutral" to Color.rgb(169, 255, 242),
        "Negative" to Color.rgb(170, 240, 255)
    )
    val weekOfDays = listOf("日", "月", "火", "水", "木", "金", "土")
    val hours : MutableList<String> = mutableListOf()
    init {
        for ( i in 0..24){
            hours.add("$i:00")
        }
    }

    val modes = listOf(
        "☹️",
        "\uD83D\uDE41",
        "\uD83D\uDE10",
        "\uD83D\uDE42",
        "\uD83D\uDE00"
    )

    val lineEntries = MutableLiveData<List<Entry>>()
    val pieEntries = MutableLiveData<List<PieEntry>>()
    val liveDate = MutableLiveData<String>()

    init {
        liveDate.postValue("${date.get(Calendar.YEAR)}/${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.DATE)}")
    }

    private val usecase = ChartActivityUsecase()

    fun onRangeSelected(field: Int) {
        this.rangeField = field
        requestData()
    }

    fun waverRange(viewId: Int) {
        val value = if (viewId == R.id.next) 1 else -1
        date.add(rangeField, value)
        requestData()
    }

    private fun requestData() {
        when (rangeField) {
            Calendar.DATE -> {
                liveDate.postValue("${date.get(Calendar.YEAR)}/${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.DATE)}")
                requestDataOfDate()
            }
            Calendar.WEEK_OF_YEAR -> {
                liveDate.postValue("${date.firstDayOfWeek}-${date.firstDayOfWeek + 6}")
            }
            Calendar.MONTH -> {
                liveDate.postValue("${date.get(Calendar.YEAR)}/${date.get(Calendar.MONTH) + 1}")
                requestDataOfMonth()
            }
        }
    }

    private fun requestDataOfDate() {

        GlobalScope.launch {
            val datas = usecase.getDataOfDate(date)
            val lineData = datas.first
            val pieData = datas.second
            launch(Dispatchers.IO) {
                lineEntries.postValue(lineData)
                pieEntries.postValue(pieData)
            }
        }
    }

    private fun requestDataOfWeek() {
        GlobalScope.launch {
            val firstDayOfWeek = date.firstDayOfWeek
            val datas = usecase.getDataOfWeek(firstDayOfWeek.toString())
            val lineData = datas.first
            val pieData = datas.second
            launch(Dispatchers.IO) {
                lineEntries.postValue(lineData)
                pieEntries.postValue(pieData)
            }
        }
    }

    private fun requestDataOfMonth() {
        GlobalScope.launch {
            val datas = usecase.getDataOfMonth(date)
            val lineData = datas.first
            val pieData = datas.second
            launch(Dispatchers.IO) {
                lineEntries.postValue(lineData)
                pieEntries.postValue(pieData)
            }
        }
    }

    fun getPieChartData(): PieData {
        val pieDataSet = PieDataSet(pieEntries.value, "").apply {
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
        Collections.sort(lineEntries.value, EntryXComparator())
        val lineDataSet = LineDataSet(lineEntries.value, "投稿")
            .apply {
                lineWidth = 2.5f
                circleRadius = 5f
                setDrawValues(false)
            }
        return LineData(lineDataSet)
    }

    private fun getPieChartColorList(): List<Int> {
        val colorList = mutableListOf<Int>()
        pieEntries.value?.forEach {
            val color = pieColorsMap[it.label] ?: Color.rgb(169, 255, 242)
            colorList.add(color)
        }
        return colorList
    }

    fun getDaysOfMonth(): List<String> {
        val list = mutableListOf<String>()
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        for (i in 1..date.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            list.add("$month/$i")
        }
        return list
    }

}