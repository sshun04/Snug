package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import com.shojishunsuke.kibunnsns.model.Post

interface DataBaseRepository {

    suspend fun loadFilteredCollection(fieldName: String, params: Any): List<Post>
    suspend fun savePost(post: Post)
    suspend fun loadWholeCollection(): List<Post>
}
