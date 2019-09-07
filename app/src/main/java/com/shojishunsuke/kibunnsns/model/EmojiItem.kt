package com.shojishunsuke.kibunnsns.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_emoji")
data class EmojiItem(@PrimaryKey val emojiCode: String,
                     @ColumnInfo val date: Long) : Item