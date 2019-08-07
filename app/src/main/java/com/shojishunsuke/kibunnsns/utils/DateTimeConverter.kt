package com.shojishunsuke.kibunnsns.utils

import androidx.room.TypeConverter
import java.util.*

class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }

    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {

        return date?.time?.toLong()
    }
}