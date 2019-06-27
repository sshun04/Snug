package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import androidx.room.*
import com.shojishunsuke.kibunnsns.model.LocalPost

@Dao
interface LoacalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createLocalPost(post:LocalPost)

    @Query("SELECT * FROM LocalPost")
    fun findAll():List<LocalPost>

    @Update
    fun updateLocalPost(post:LocalPost)

    @Delete
    fun delete(post: LocalPost)
}