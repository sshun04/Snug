package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.CalendarFragmentUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CalendarActivityViewModel:ViewModel() {

    val postsOfDate = MutableLiveData<List<Post>>()
    val dateText = MutableLiveData<String>()
    private val date = Calendar.getInstance()
    private val useCase = CalendarFragmentUsecase()
    init {
        onFocusToday()
    }

    fun setDate(year:Int,month:Int,date:Int){
        this.date.set(year, month, date)
        requestPostsByDate()
    }

    fun getDateInLong():Long = date.time.time

    fun onFocusToday(){
        date.time = Date()
        requestPostsByDate()
    }

    private fun requestPostsByDate(){
        val dateString = "${date.get(Calendar.MONTH)+1}/${date.get(Calendar.DAY_OF_MONTH)}"
        dateText.postValue(dateString)
        GlobalScope.launch {
            val posts = useCase.loadPostsByDate(date)
            launch (Dispatchers.IO){
                postsOfDate.postValue(posts)
            }
        }
    }
}