package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import com.shojishunsuke.kibunnsns.model.Post

interface DataBaseRepository {
    suspend fun savePost(post: Post)
    suspend fun loadSortedNextCollection(basePost: Post): List<Post>
    suspend fun loadFollowingCollection(previousPost: Post): List<Post>
}
