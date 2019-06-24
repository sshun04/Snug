package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.EmojiRepositoy
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostDialogUseCase {
    private val emojiRepository = EmojiRepositoy()

    fun loadWholeEmoji():List<String> = emojiRepository.loadWholeEmoji()

    fun loadCurrentEmoji():List<String> = emojiRepository.loadCurrentEmoji()
}