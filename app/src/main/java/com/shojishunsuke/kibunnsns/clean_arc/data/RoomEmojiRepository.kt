package com.shojishunsuke.kibunnsns.clean_arc.data

import com.shojishunsuke.kibunnsns.MainApplication
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LocalDataBaseRepository
import com.shojishunsuke.kibunnsns.model.EmojiItem
import com.shojishunsuke.kibunnsns.model.Item
import java.util.*

class RoomEmojiRepository : LocalDataBaseRepository {

    private val dao = MainApplication.emojiDatabase.emojiDao()

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

    private fun deleteItem(item: Item) {
        dao.delete(item as EmojiItem)
    }
}
