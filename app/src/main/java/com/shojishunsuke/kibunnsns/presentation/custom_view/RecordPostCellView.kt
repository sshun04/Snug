package com.shojishunsuke.kibunnsns.presentation.custom_view

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.PopupMenu
import androidx.cardview.widget.CardView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.domain.model.Post
import com.shojishunsuke.kibunnsns.ext.detailDateString
import com.shojishunsuke.kibunnsns.ext.timeInDay
import kotlinx.android.synthetic.main.item_post_record_detail.view.*
import java.util.*

class RecordPostCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val calendar: Calendar = Calendar.getInstance()

    fun build(post: Post, listener: (Post) -> Unit) {
        calendar.time = post.date
        detailDateTextView.text = calendar.detailDateString()
        timeTextView.text = post.date.timeInDay()

        popMenuButton.setOnClickListener {
            val popupMenu = PopupMenu(context, it, Gravity.END)
            popupMenu.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.deletePost -> {
                        AlertDialog.Builder(context)
                            .setPositiveButton("削除") { _, _ ->
                                listener(post)
                            }
                            .setNegativeButton("キャンセル", null)
                            .setMessage("本当に削除しますか？")
                            .show()
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.inflate(R.menu.record_popup_menu)
            popupMenu.show()
        }

        val description = getSentiDescription(post.sentiScore)
        sentiDescription.apply {
            text = description.first
            setBackgroundResource(description.second)
        }

        numberOfViews.text = post.views.toString()
        contentTextView.text = post.contentText
        activityIconTextView.text = if (post.actID.isNotBlank()) post.actID else ""
    }

    private fun getSentiDescription(sentiScore: Float): Pair<String, Int> {
        return when {
            sentiScore > 0.3f -> Pair("Positive", R.drawable.textview_back_positive)
            sentiScore < -0.3f -> Pair("Negative", R.drawable.textview_back_negative)
            else -> Pair("Neutral", R.drawable.textview_back_neutral)
        }
    }
}