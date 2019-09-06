package com.shojishunsuke.kibunnsns.model

import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable
import java.util.*

@IgnoreExtraProperties
data class CloudPost(
    override val postId: String = UUID.randomUUID().toString(),
    override val userId: String = "",
    override var userName: String = "",
    override val iconPhotoLink: String = "",
    override val contentText: String = "",
    override val sentiScore: Float = 0.0f,
    override val magnitude: Float = 0.0f,
    override val date: Date = Date(),
    override val actID: String = "",
    override val keyWord: String = "",
    override val views: Int = 0
) : Post, Serializable {}