package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import com.shojishunsuke.kibunnsns.model.Post
import java.util.*

interface DataBaseRepository {
    suspend fun savePost(post: Post)
    suspend fun loadSpecificSortedNextCollection(basePost: Post): List<Post>
    suspend fun loadFollowingCollection(date: Date): List<Post>
    suspend fun loadPositiveTimeLineCollection(date: Date):List<Post>


}
