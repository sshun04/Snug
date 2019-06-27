package com.shojishunsuke.kibunnsns.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalPost(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "post") val post: Post
) : Item {
}