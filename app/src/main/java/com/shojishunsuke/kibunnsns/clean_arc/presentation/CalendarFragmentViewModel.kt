package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.github.sundeepk.compactcalendarview.domain.Event
import com.shojishunsuke.kibunnsns.clean_arc.domain.CalendarFragmentUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CalendarFragmentViewModel : ViewModel() {

    val postsOfDate: LiveData<MutableList<Post>> get() = _postsOfDate
    private val _postsOfDate = MutableLiveData<MutableList<Post>>()


    val dateText: LiveData<String> get() = _dateText
    private val _dateText = MutableLiveData<String>()

    private val date = Calendar.getInstance()
    private val useCase = CalendarFragmentUsecase()

    val eventDateList: LiveData<MutableList<Event>> = useCase.postedDate.map {
        it.map {
            Event(Color.rgb(149, 235, 222), it.dateInLong)
        }.toMutableList()
    }

    fun onPostRemoved(post: Post) {}

    fun setDate(date: Date) {
        this.date.time = date
        requestPostsByDate()
    }

    fun getDate(): Date = date.time

    fun onFocusToday() {
        date.time = Date()
        requestPostsByDate()
    }


    fun refresh() {
        _postsOfDate.value?.clear()
        onFocusToday()
    }

    private fun requestPostsByDate() {
        val dateString = "${date.get(Calendar.MONTH) + 1}/${date.get(Calendar.DAY_OF_MONTH)}"
        _dateText.postValue(dateString)
        GlobalScope.launch {
            val posts = useCase.loadPostsByDate(date)
            launch(Dispatchers.IO) {
                _postsOfDate.postValue(posts)
            }
        }
    }
}
