package com.shojishunsuke.kibunnsns

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.room.Database
import androidx.room.Room
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LocalDatabase

class MainApplication : Application() {
    companion object{
        lateinit var database: LocalDatabase
    }

    override fun onCreate() {

        val emojiConfig = BundledEmojiCompatConfig(this)
        emojiConfig.setReplaceAll(true)
        EmojiCompat.init(emojiConfig)

        database = Room.databaseBuilder(this,LocalDatabase::class.java,"kibunn_sns.db").build()

        super.onCreate()
    }
}