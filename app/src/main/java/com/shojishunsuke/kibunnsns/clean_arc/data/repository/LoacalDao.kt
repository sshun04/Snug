package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import androidx.room.*
import com.shojishunsuke.kibunnsns.model.LocalPost
import com.shojishunsuke.kibunnsns.model.User

@Dao
interface LoacalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createLocalPost(post: LocalPost)

    @Query("SELECT * FROM user")
    fun findUser():List<User>

    @Query("SELECT * FROM post_table")
    fun findAll(): List<LocalPost>

    @Query("UPDATE user SET name =:name WHERE id =:id")
    fun updateUserName(name: String, id: Int)

    @Query("UPDATE user SET iconUri =:iconUri WHERE id=:id")
    fun updateIcon(iconUri: String, id: Int)

    @Query("UPDATE user SET appTheme=:themeInfo WHERE id=:id")
    fun updateTheme(themeInfo:Int,id: Int)

    @Update
    fun updateLocalPost(post: LocalPost)

    @Delete
    fun delete(post: LocalPost)
}