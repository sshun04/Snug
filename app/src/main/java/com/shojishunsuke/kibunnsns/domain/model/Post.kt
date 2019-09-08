package com.shojishunsuke.kibunnsns.domain.model

import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable
import java.util.*

@IgnoreExtraProperties
data class Post(
        val postId: String = UUID.randomUUID().toString(),
        val userId: String = "",
        var userName: String = "",
        val iconPhotoLink: String = "",
        val contentText: String = "",
        val sentiScore: Float = 0.0f,
        val magnitude: Float = 0.0f,
        val date: Date = Date(),
        val actID: String = "",
        val keyWord: String = "",
        val views: Int = 0
) : Item, Serializable