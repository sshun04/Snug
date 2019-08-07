package com.shojishunsuke.kibunnsns.clean_arc.data

import com.shojishunsuke.kibunnsns.MainApplication
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LocalDataBaseRepository
import com.shojishunsuke.kibunnsns.model.EmojiItem
import com.shojishunsuke.kibunnsns.model.Item
import java.util.*

class RoomRepository : LocalDataBaseRepository {

    private val dao = MainApplication.database.emojiDao()

    override suspend fun loadLatestCollection(): List<Item> {
        return dao.findAll()
    }

    override suspend fun registerItem(value: String) {
        val emojiItem = EmojiItem(value,Date().time)
        val currentList = dao.findAll()
        if (currentList.size > 8){
            deleteItem(currentList.first())
        }

        dao.registerEmoji(emojiItem)
    }

    private fun deleteItem(item: Item) {
        dao.delete(item as EmojiItem)
    }
}
