package com.shojishunsuke.kibunnsns.data.repository.impl

import com.shojishunsuke.kibunnsns.SnugApplication
import com.shojishunsuke.kibunnsns.data.repository.LocalDataBaseRepository
import com.shojishunsuke.kibunnsns.data.room.RoomEmojiDao
import com.shojishunsuke.kibunnsns.domain.model.EmojiItem
import com.shojishunsuke.kibunnsns.domain.model.Item
import java.util.*

class RoomEmojiRepository : LocalDataBaseRepository {
    private val dao: RoomEmojiDao = SnugApplication.emojiDatabase.emojiDao()

    override suspend fun loadLatestCollection(): List<Item> {
        val savedList = dao.findAll()
        return if (savedList.isNotEmpty()) {
            savedList
        } else {
            val date = Calendar.getInstance().time.time
            val emojis = listOf("\uD83C\uDFB5", "\uD83C\uDFC0", "\uD83C\uDFD6️", "\uD83C\uDF82")
            val itmeList = mutableListOf<EmojiItem>()
            for (emoji in emojis) {
                itmeList.add(EmojiItem(emoji, date))
            }
            itmeList
        }
    }

    override suspend fun registerItem(value: String) {
        val emojiItem = EmojiItem(value, Date().time)
        val currentList = dao.findAll()
        if (currentList.size > 8) {
            deleteItem(currentList.first())
        }
        dao.registerEmoji(emojiItem)
    }

    override fun deleteItem(item: Item) {
        dao.delete(item as EmojiItem)
    }
}
