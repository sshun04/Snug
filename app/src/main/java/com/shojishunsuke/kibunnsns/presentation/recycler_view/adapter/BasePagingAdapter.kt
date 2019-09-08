package com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.domain.model.Post
import java.text.SimpleDateFormat
import java.util.*

abstract class BasePagingAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val posts: MutableList<Post> = mutableListOf()

    var viewType: Int = 1

    override fun getItemViewType(position: Int): Int = viewType

    override fun getItemCount(): Int = posts.size

    fun addNextCollection(nextPosts: List<Post>) {
        nextPosts.forEach { add(it) }
    }

    fun clear() {
        posts.clear()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        posts.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    protected fun getAppropriateIconFromSentiScore(sentiScore: Float): String {
        return when {
            sentiScore > 0.3f -> "\uD83D\uDE01"
            sentiScore <= 0.3f && sentiScore >= -0.3f -> "\uD83D\uDE10"
            sentiScore < -0.3f -> "☹️"
            else -> "\uD83D\uDE10"
        }
    }

    protected fun getSentiColorId(sentiScore: Float): Int {
        return when {
            sentiScore > 0.3f -> R.color.color_positive
            sentiScore < -0.3f -> R.color.color_negative
            else -> R.color.color_neutral
        }
    }

    protected fun getSentiDescription(sentiScore: Float): Pair<String, Int> {
        return when {
            sentiScore > 0.3f -> Pair("Positive", R.drawable.textview_back_positive)
            sentiScore < -0.3f -> Pair("Negative", R.drawable.textview_back_negative)
            else -> Pair("Neutral", R.drawable.textview_back_neutral)
        }
    }

    private fun add(post: Post) {
        val position = posts.size
        posts.add(position, post)
        notifyItemInserted(position)
    }
}