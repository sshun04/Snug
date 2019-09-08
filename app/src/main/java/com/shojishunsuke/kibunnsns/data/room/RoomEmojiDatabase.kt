package com.shojishunsuke.kibunnsns.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shojishunsuke.kibunnsns.domain.model.EmojiItem

@Database(entities = arrayOf(EmojiItem::class), version = 1)
abstract class RoomEmojiDatabase : RoomDatabase() {
    abstract fun emojiDao(): RoomEmojiDao
}