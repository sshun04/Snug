package com.shojishunsuke.kibunnsns

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.room.Room
import com.shojishunsuke.kibunnsns.data.room.RoomEmojiDatabase
import com.shojishunsuke.kibunnsns.data.room.RoomPostDatabase

class MainApplication : Application() {
    companion object {
        lateinit var emojiDatabase: RoomEmojiDatabase
        lateinit var postDateDatabase: RoomPostDatabase
    }

    override fun onCreate() {

        val emojiConfig = BundledEmojiCompatConfig(this)
        emojiConfig.setReplaceAll(true)
        EmojiCompat.init(emojiConfig)

        emojiDatabase =
                Room.databaseBuilder(this, RoomEmojiDatabase::class.java, "kibunn_emoji.db").build()
        postDateDatabase =
                Room.databaseBuilder(this, RoomPostDatabase::class.java, "kibunn_post_date.db").build()

        super.onCreate()
    }
}