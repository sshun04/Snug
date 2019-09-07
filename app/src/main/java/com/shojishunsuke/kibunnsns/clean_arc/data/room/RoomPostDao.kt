package com.shojishunsuke.kibunnsns.clean_arc.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shojishunsuke.kibunnsns.model.PostedDate

@Dao
interface RoomPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerDate(date: PostedDate)

    @Query("SELECT * FROM table_posted_date")
    fun findAll(): LiveData<List<PostedDate>>

    @Delete
    fun deleteDate(date: PostedDate)
}