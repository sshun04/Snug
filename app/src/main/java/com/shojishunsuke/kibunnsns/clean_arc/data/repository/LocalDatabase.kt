package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shojishunsuke.kibunnsns.clean_arc.utils.DateTimeConverter
import com.shojishunsuke.kibunnsns.model.LocalPost

@Database(entities = arrayOf(LocalPost::class),version = 2)
@TypeConverters(DateTimeConverter::class)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun localDao():LoacalDao


}