package com.shojishunsuke.kibunnsns.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.shojishunsuke.kibunnsns.GlideApp
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.domain.model.Post
import kotlinx.android.synthetic.main.item_post.view.*

class StaggerdPostCellView @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet? = null,
    defStyleAttr:Int = 0
):CardView(context, attrs, defStyleAttr) {

    fun build(post:Post,listener: (Post) -> Unit){
        userName.text = if (post.userName.isNotBlank()) post.userName else "匿名"
        contentTextView.text = post.contentText
        timeTextView.text = post.date.toString()
        if (post.actID.isNotBlank()){
            activityIcon.visibility = View.VISIBLE
            activityIcon.text = post.actID
        }else{
            activityIcon.visibility = View.GONE
        }

        if(post.iconPhotoLink.isNotBlank()) {
            GlideApp.with(this)
                .load(post.iconPhotoLink)
                .placeholder(R.drawable.usericon_default)
                .error(R.drawable.usericon_default)
                .into(userIcon)
        }else{
            userIcon.setImageResource(R.drawable.usericon_default)
        }

        postBaseView.setOnClickListener {
            listener(post)
        }
    }
}