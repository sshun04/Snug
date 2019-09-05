package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import com.shojishunsuke.kibunnsns.model.Item

interface LocalDataBaseRepository {
    suspend fun loadLatestCollection(): List<Item>
    suspend fun registerItem(value: String)
    fun deleteItem(item: Item)
}