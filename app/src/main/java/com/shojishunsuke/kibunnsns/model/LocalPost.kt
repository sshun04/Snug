package com.shojishunsuke.kibunnsns.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shojishunsuke.kibunnsns.model.Item
import com.shojishunsuke.kibunnsns.model.Post
import java.util.*

@Entity(tableName = "post_table")
data class LocalPost(
    @PrimaryKey(autoGenerate = true) var id:Long = 0L,
    @ColumnInfo val userName: String = "",
    @ColumnInfo val contentText:String,
    @ColumnInfo val date : Date,
    @ColumnInfo val actId : String
) : Item {
}