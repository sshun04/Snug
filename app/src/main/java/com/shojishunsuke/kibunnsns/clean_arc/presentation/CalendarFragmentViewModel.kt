package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.graphics.Color
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.sundeepk.compactcalendarview.domain.Event
import com.shojishunsuke.kibunnsns.clean_arc.domain.CalendarFragmentUsecase
import com.shojishunsuke.kibunnsns.model.Post
import com.shojishunsuke.kibunnsns.model.PostedDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CalendarFragmentViewModel(private val viewLifecycleOwner: LifecycleOwner) : ViewModel() {

    private val _postsOfDate = MutableLiveData<List<Post>>()
    val postsOfDate: LiveData<List<Post>> get() = _postsOfDate

    private val _dateText = MutableLiveData<String>()
    val dateText: LiveData<String> get() = _dateText

    private val date = Calendar.getInstance()
    private val useCase = CalendarFragmentUsecase()


    private val _postedDate = useCase.postedDate.apply {
        observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val eventList = mutableListOf<Event>()
            it.forEach {
                val event = Event(Color.rgb(149, 235, 222), it.dateInLong)
                eventList.add(event)
            }

            eventDateList.postValue(eventList)
        })
    }
    val eventDateList = MutableLiveData<List<Event>>()

    init {
        onFocusToday()
    }

    fun setDate(date: Date) {
        this.date.time = date
        requestPostsByDate()
    }

    fun getDate(): Date = date.time

    fun onFocusToday() {
        date.time = Date()
        requestPostsByDate()
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