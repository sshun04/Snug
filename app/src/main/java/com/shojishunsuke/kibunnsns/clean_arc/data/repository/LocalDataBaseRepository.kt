package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import com.shojishunsuke.kibunnsns.model.EmojiItem
import com.shojishunsuke.kibunnsns.model.Post

interface LocalDataBaseRepository {
    suspend fun loadLatestCollection(): List<EmojiItem>
    suspend fun registerItem(value: String)
    fun deleteItem(emojiItem: EmojiItem)
}