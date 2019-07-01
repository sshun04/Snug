package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shojishunsuke.kibunnsns.clean_arc.utils.DateTimeConverter
import com.shojishunsuke.kibunnsns.model.LocalPost
import com.shojishunsuke.kibunnsns.model.User

@Database(entities = arrayOf(User::class,LocalPost::class),version = 3)
@TypeConverters(DateTimeConverter::class)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun localDao():LoacalDao


}