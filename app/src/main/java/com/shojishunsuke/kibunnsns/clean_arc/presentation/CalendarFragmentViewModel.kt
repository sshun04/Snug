package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.CalendarFragmentUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CalendarFragmentViewModel:ViewModel() {

    val postsByDate = MutableLiveData<List<Post>>()
    private val useCase = CalendarFragmentUsecase()

    fun requestPostsByDate(date:String){
        GlobalScope.launch {
            val posts = useCase.loadPostsByDate(date)
            launch (Dispatchers.IO){
                postsByDate.postValue(posts)
            }
        }
    }
}