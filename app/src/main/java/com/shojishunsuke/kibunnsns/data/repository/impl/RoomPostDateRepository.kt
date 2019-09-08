package com.shojishunsuke.kibunnsns.data.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.shojishunsuke.kibunnsns.SnugApplication
import com.shojishunsuke.kibunnsns.data.room.RoomPostDao
import com.shojishunsuke.kibunnsns.domain.model.PostedDate
import java.util.*

class RoomPostDateRepository {
    private val dao : RoomPostDao = SnugApplication.postDateDatabase.postDateDao()

    fun registerDate(date: Date) {
        val dateInLong = date.time
        dao.registerDate(PostedDate(dateInLong))
    }

    fun loadDateList(): LiveData<MutableList<PostedDate>> = dao.findAll().map { it.toMutableList() }

    fun deleteDate(date: Date) {
        val postedDate = PostedDate(date.time)
        dao.deleteDate(postedDate)
    }
}