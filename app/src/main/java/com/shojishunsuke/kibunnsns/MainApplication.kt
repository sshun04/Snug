package com.shojishunsuke.kibunnsns

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat

class MainApplication : Application() {
    override fun onCreate() {

        val emojiConfig = BundledEmojiCompatConfig(this)
        EmojiCompat.init(emojiConfig)
        super.onCreate()
    }
}