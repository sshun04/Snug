package com.shojishunsuke.kibunnsns.data.repository

import com.shojishunsuke.kibunnsns.domain.model.Post
import java.util.*

interface DataBaseRepository {
    suspend fun savePost(post: Post)
    suspend fun loadSpecificSortedNextCollection(basePost: Post): List<Post>
    suspend fun loadFollowingCollection(date: Date): List<Post>
    suspend fun loadPositiveTimeLineCollection(date: Date): List<Post>
    suspend fun loadDateRangedCollection(
            userId: String,
            oldDate: Date,
            currentDate: Date,
            limit: Long = 100
    ): MutableList<Post>
    fun deleteItemFromDatabase(post: Post)
    suspend fun loadScoreRangedCollectionAscend(limit: Long = 20, post: Post): MutableList<Post>
    suspend fun loadScoreRangedCollectionDescend(limit: Long = 20, post: Post): MutableList<Post>
}
