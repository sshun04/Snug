package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import com.shojishunsuke.kibunnsns.model.Item
import com.shojishunsuke.kibunnsns.model.Post

interface DataBaseRepository {

    suspend fun loadFilteredCollection(
        sentiScore: Float,
        magnitude: Float,
        keyWord: String,
        activityCode: String
    ): List<Item>

    suspend fun savePost(post: Post)
    suspend fun loadWholeCollection(): List<Item>
}
