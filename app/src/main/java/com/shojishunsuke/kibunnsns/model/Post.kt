package com.shojishunsuke.kibunnsns.model

import android.provider.ContactsContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.IgnoreExtraProperties
import java.math.BigDecimal
import java.util.*

@IgnoreExtraProperties
data class Post(
    val userId :String = "",
    var userName:String = "",
    val iconPhotoLink :String = "",
    val contentText:String = "",
    val sentiScore :Float = 0.0f,
    val magnitude:Float = 0.0f,
    val date : Date = Date(),
    val actID  : String = "",
    val keyWord : String = ""
):Item{}