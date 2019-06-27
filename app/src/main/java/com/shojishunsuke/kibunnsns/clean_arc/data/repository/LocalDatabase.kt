package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(LoacalDao::class),version = 1)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun localDao():LoacalDao


}