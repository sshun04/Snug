package com.shojishunsuke.kibunnsns.clean_arc.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shojishunsuke.kibunnsns.model.EmojiItem

@Database(entities = arrayOf(EmojiItem::class), version = 1)
abstract class RoomEmojiDatabase : RoomDatabase() {
    abstract fun emojiDao(): RoomEmojiDao
}