package com.shojishunsuke.kibunnsns.clean_arc.data.room

import androidx.room.*
import com.shojishunsuke.kibunnsns.model.EmojiItem

@Dao
interface RoomEmojiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerEmoji(emojiItem: EmojiItem)

    @Query("SELECT * FROM table_emoji")
    fun findAll(): List<EmojiItem>

    @Delete
    fun delete(emojiItem: EmojiItem)
}