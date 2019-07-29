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
    val livedataDate = MutableLiveData<String>()


    init {
        livedataDate.postValue("${date.get(Calendar.YEAR)}/${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.DATE)}")
    }

    private val usecase = ChartActivityUsecase()

    fun onRangeSelected(field: Int) {
        rangeField = field
        requestDataOfDate()
    }

    fun waverRange(value: Int) {
        date.add(rangeField, value)
        when (rangeField) {
            Calendar.DATE -> {
                livedataDate.postValue("${date.get(Calendar.YEAR)}/${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.DATE)}")
            }
            Calendar.WEEK_OF_YEAR -> {
                livedataDate.postValue("${date.firstDayOfWeek}")
            }
            Calendar.MONTH -> {
                livedataDate.postValue("${date.get(Calendar.YEAR)}/${date.get(Calendar.MONTH) + 1}")
            }
        }
        requestDataOfDate()
    }

    private fun requestDataOfDate() {

        GlobalScope.launch {
            val date = "${date.get(Calendar.YEAR)}/${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.DATE)}"
            val datas = usecase.requestDataOfDate(date)
            val lineData = datas.first
            val pieData = datas.second
            launch(Dispatchers.IO) {
                lineEntries.postValue(lineData)
                pieEntries.postValue(pieData)
            }
        }
    }


}