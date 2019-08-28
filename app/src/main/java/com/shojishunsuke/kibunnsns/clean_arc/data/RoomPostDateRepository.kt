package com.shojishunsuke.kibunnsns.clean_arc.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.shojishunsuke.kibunnsns.MainApplication
import com.shojishunsuke.kibunnsns.model.PostedDate
import com.shojishunsuke.kibunnsns.utils.dayOfMonth
import com.shojishunsuke.kibunnsns.utils.month
import com.shojishunsuke.kibunnsns.utils.year
import java.util.*

class RoomPostDateRepository {
    private val dao = MainApplication.postDateDatabase.postDateDao()

    fun registerDate(date: Date){
        val dateInLong = date.time
        dao.registerDate(PostedDate(dateInLong))
    }

    fun loadDateList(): LiveData<MutableList<PostedDate>> = dao.findAll().map {it.toMutableList()}

    fun deleteDate(date: Date){
        val postedDate = PostedDate(date.time)
        dao.deleteDate(postedDate)
    }
}