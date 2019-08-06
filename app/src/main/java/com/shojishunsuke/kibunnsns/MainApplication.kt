package com.shojishunsuke.kibunnsns

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.room.Room
import com.shojishunsuke.kibunnsns.clean_arc.data.room.RoomEmojiDatabase

class MainApplication : Application() {
    companion object {
        lateinit var database: RoomEmojiDatabase
    }
    override fun onCreate() {

        val emojiConfig = BundledEmojiCompatConfig(this)
        emojiConfig.setReplaceAll(true)
        EmojiCompat.init(emojiConfig)

        database = Room.databaseBuilder(this,RoomEmojiDatabase::class.java,"kibunn_emoji.db").build()

        super.onCreate()
    }
}