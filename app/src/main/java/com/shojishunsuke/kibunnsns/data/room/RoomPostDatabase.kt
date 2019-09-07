package com.shojishunsuke.kibunnsns.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shojishunsuke.kibunnsns.domain.model.PostedDate

@Database(entities = arrayOf(PostedDate::class), version = 1)
abstract class RoomPostDatabase : RoomDatabase() {
    abstract fun postDateDao(): RoomPostDao
}