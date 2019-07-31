package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.shojishunsuke.kibunnsns.clean_arc.domain.ChartActivityUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ChartActivityViewModel : ViewModel() {

    private var rangeField = Calendar.DATE
    private val date: Calendar = Calendar.getInstance()

    val lineEntries = MutableLiveData<List<Entry>>()
    val pieEntries = MutableLiveData<List<PieEntry>>()
    val liveDate = MutableLiveData<String>()


    init {
        liveDate.postValue("${date.get(Calendar.YEAR)}/${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.DATE)}")
    }

    private val usecase = ChartActivityUsecase()

    fun onRangeSelected(field: Int) {
        this.rangeField = field
        when (rangeField) {
            Calendar.DATE -> requestDataOfDate()
            Calendar.WEEK_OF_YEAR -> requestDataOfWeek()
            Calendar.MONTH -> requestDataOfMonth()
        }
    }

    fun waverRange(value: Int) {
        date.add(rangeField, value)
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
            }
        }

    }

    private fun requestDataOfDate() {

        GlobalScope.launch {
            val date = "${date.get(Calendar.YEAR)}/${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.DATE)}"
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
            val year = date.get(Calendar.YEAR)
            val month = date.get(Calendar.MONTH)
            val yearMonth = "$year/$month"
            val daysOfMonth = date.getActualMaximum(Calendar.DAY_OF_MONTH)
            val datas = usecase.getDataOfMonth(yearMonth, daysOfMonth)
            val lineData = datas.first
            val pieData = datas.second
            launch(Dispatchers.IO) {
                lineEntries.postValue(lineData)
                pieEntries.postValue(pieData)
            }
        }
    }





}