package com.shojishunsuke.kibunnsns.model

import android.provider.ContactsContract
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Post(
    val contentText:String = "",
    var sentiScore :Int = 0,
    val date : Date = Date(),
    val actID  : String = "soccer"
){}