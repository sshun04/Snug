package com.shojishunsuke.kibunnsns.clean_arc.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shojishunsuke.kibunnsns.model.PostedDate

@Database(entities = arrayOf(PostedDate::class), version = 1)
abstract class RoomPostDatabase : RoomDatabase() {
    abstract fun postDateDao(): RoomPostDao
}