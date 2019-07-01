package com.shojishunsuke.kibunnsns.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int = 0,
    var name: String = "匿名",
    var iconUri: String = "",
    var appTheme: Int = 0
)