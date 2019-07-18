package com.shojishunsuke.kibunnsns

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.room.Room

class MainApplication : Application() {
    override fun onCreate() {

        val emojiConfig = BundledEmojiCompatConfig(this)
        emojiConfig.setReplaceAll(true)
        EmojiCompat.init(emojiConfig)

        super.onCreate()
    }
}