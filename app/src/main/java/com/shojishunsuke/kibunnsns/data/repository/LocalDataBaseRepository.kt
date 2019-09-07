package com.shojishunsuke.kibunnsns.data.repository

import com.shojishunsuke.kibunnsns.domain.model.Item

interface LocalDataBaseRepository {
    suspend fun loadLatestCollection(): List<Item>
    suspend fun registerItem(value: String)
    fun deleteItem(item: Item)
}