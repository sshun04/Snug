package com.shojishunsuke.kibunnsns.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.shojishunsuke.kibunnsns.GlideApp
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.domain.model.Post
import com.shojishunsuke.kibunnsns.ext.postedTime
import kotlinx.android.synthetic.main.item_post_vertical.view.*

class VerticalPostCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    fun build(post: Post, listener: (Post) -> Unit) {
        userName.text = post.userName
        contentTextView.text = post.contentText
        timeTextView.text = post.date.postedTime()

        activityIcon.text = post.actID

        if (post.iconPhotoLink.isNotBlank()) {
            GlideApp.with(this)
                .load(post.iconPhotoLink)
                .error(R.drawable.usericon_default)
                .placeholder(R.drawable.usericon_default)
                .into(userIcon)
        } else {
            userIcon.setImageResource(R.drawable.usericon_default)
        }

        postBaseView.setOnClickListener {
            listener(post)
        }
    }
}