package com.shojishunsuke.kibunnsns.model

import androidx.room.Entity
import java.util.*


interface Post {
    val postId:String
    val userId :String
    var userName:String
    val iconPhotoLink :String
    val contentText:String
    val sentiScore :Float
    val magnitude:Float
    val date : Date
    val actID  : String
    val keyWord : String
    val views : Int
}