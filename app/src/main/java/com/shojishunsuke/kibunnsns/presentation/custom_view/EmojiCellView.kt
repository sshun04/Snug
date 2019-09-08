package com.shojishunsuke.kibunnsns.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.item_emoji.view.*

class EmojiCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun build(unicode: String, emojiListener: (String) -> Unit) {
        emojiTextView.text = unicode
        emojiTextView.setOnClickListener {
            emojiListener(unicode)
        }
    }
}