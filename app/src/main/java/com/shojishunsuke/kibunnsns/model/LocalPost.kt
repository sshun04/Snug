package com.shojishunsuke.kibunnsns.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "local_cache")
data class LocalPost (
    @PrimaryKey  override val postId: String,
    override val userId: String,
    override var userName: String,
    override val iconPhotoLink: String,
    override val contentText: String,
    override val sentiScore:Float,
    override val magnitude: Float,
    override val date: Date,
    override val actID: String,
    override val keyWord: String,
    override val views: Int
):Post{
}