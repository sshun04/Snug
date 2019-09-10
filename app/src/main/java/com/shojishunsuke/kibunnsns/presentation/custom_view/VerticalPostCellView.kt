package com.shojishunsuke.kibunnsns.presentation.custom_view

import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.shojishunsuke.kibunnsns.GlideApp
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.domain.model.Post
import kotlinx.android.synthetic.main.item_post_vertical.view.*

class VerticalPostCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    fun build(post: Post, listener: (Post) -> Unit) {
        userName.text =
            if (post.userName.isNotBlank()) post.userName else context.resources.getString(R.string.user_name_default)
        contentTextView.text = post.contentText
        timeTextView.text = DateUtils.getRelativeTimeSpanString(
            post.date.time, System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        )

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