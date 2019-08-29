package com.shojishunsuke.kibunnsns.adapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.model.Post
import java.text.SimpleDateFormat
import java.util.*

abstract class PagingBaseAdapter<VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val posts = mutableListOf<Post>()

    var viewType = 1
    private fun add(post: Post) {
        val position = posts.size
        posts.add(position, post)
        notifyItemInserted(position)
    }


    fun addNextCollection(nextPosts: List<Post>) {
        nextPosts.forEach { add(it) }
    }

    fun clear() {
        posts.clear()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        posts.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,itemCount)
    }

    override fun getItemViewType(position: Int): Int {
        return this.viewType
    }

    override fun getItemCount(): Int = posts.size

    protected fun formatDate(postedDate: Date): String {
        val currentDate = Date()
        val timeDiffInSec = (currentDate.time - postedDate.time) / 1000

        val hourDiff = timeDiffInSec / 3600
        val minuteDiff = (timeDiffInSec % 3600) / 60
        val secDiff = timeDiffInSec % 60

        return when {
            timeDiffInSec in 3600 * 24 until 3600 * 48 -> {
                "昨日"
            }
            timeDiffInSec in 3600 until 3600 * 24 -> {
                "$hourDiff" + "時間前"
            }
            timeDiffInSec in 360 until 3600 -> {
                "$minuteDiff" + "分前"
            }
            timeDiffInSec < 360 -> {
                "$secDiff" + "秒前"
            }
            else -> {
                val formatter = SimpleDateFormat("MM月dd日", Locale.JAPAN)
                formatter.format(postedDate)
            }
        }

    }

    protected fun takeTimeFromDate(date: Date): String {
        val formatter = SimpleDateFormat("HH:mm", Locale.JAPAN)
        return formatter.format(date)
    }

    protected fun getAppropriateIconFromSentiScore(sentiScore: Float): String {
        return when {
            sentiScore > 0.4f -> "\uD83D\uDE01"
            sentiScore <= 0.4f && sentiScore >= -0.4f -> "\uD83D\uDE10"
            sentiScore < -0.4f -> "☹️"
            else -> "\uD83D\uDE10"
        }
    }

    protected fun getSentiColorId(sentiScore: Float):Int{
        return when {
            sentiScore > 0.4f -> R.color.color_positive
            sentiScore < -0.4f -> R.color.color_negative
            else -> R.color.color_neutral
        }
    }



    protected fun getSentiDescription(sentiScore: Float):Pair<String,Int>{
        return when {
            sentiScore > 0.4f -> Pair("Positive",R.drawable.textview_back_positive)
            sentiScore < -0.4f -> Pair("Negative", R.drawable.textview_back_negative)
            else -> Pair("Neutral",R.drawable.textview_back_neutral)
        }
    }


}