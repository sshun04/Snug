package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.EmojiRepositoy

class PostDialogUseCase() {
    private val emojiRepository = EmojiRepositoy()

    fun loadWholeEmoji():List<String> = emojiRepository.loadWholeEmoji()

    fun loadCurrentEmoji():List<String> = emojiRepository.loadCurrentEmoji()
}