package com.shojishunsuke.kibunnsns.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.shojishunsuke.kibunnsns.GlideApp
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.model.Post
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class CustomPagingAdapter(
    private val context: Context,
    pagingOptions: FirestorePagingOptions<Post>,
    private val firstLoadedListenre: () -> Unit,
    private val tapListener: (Post) -> Unit
) :
    FirestorePagingAdapter<Post, CustomPagingAdapter.PostViewHolder>(pagingOptions) {

    private val inflater = LayoutInflater.from(context)
    private var isFirstLoad = true

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, post: Post) {
        holder.userNameTextView.text = if (post.userName.isNotBlank()) post.userName else "匿名"
        holder.contentTextView.text = post.contentText
        holder.sentiScoreTextView.text = post.sentiScore.toString()
        holder.dateTextView.text = formatDate(post.date)
        holder.activityIcon.text =
            if (post.actID.isNotBlank()) post.actID else "\uD83D\uDE42"

        if (post.iconPhotoLink.isNotBlank()) {
            GlideApp.with(context)
                .load(post.iconPhotoLink)
                .into(holder.userIcon)
        } else {
            holder.userIcon.setImageResource(R.color.colorPrimary)
        }

        holder.cardView.setOnClickListener {
            tapListener(post)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val mView = inflater.inflate(R.layout.item_post, parent, false)
        return PostViewHolder(mView)
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL -> {
            }
            LoadingState.LOADING_MORE -> {
            }
            LoadingState.LOADED -> {
//
                if (isFirstLoad) {
                    notifyDataSetChanged()
                    firstLoadedListenre()
                    isFirstLoad = false
                }
            }
            LoadingState.ERROR -> {
            }
        }

    }

    private fun formatDate(postedDate: Date): String {
        val currentDate = Date()
        val timeDiffInSec = (currentDate.time - postedDate.time) / 1000

        val hourDiff = timeDiffInSec / 3600
        val minuteDiff = (timeDiffInSec % 3600) / 60
        val secDiff = timeDiffInSec % 60

        val outPutText = when {
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
        return outPutText

    }


    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView = view.findViewById<LinearLayout>(R.id.postBaseView)
        val userNameTextView = view.findViewById<TextView>(R.id.userName)
        val userIcon = view.findViewById<CircleImageView>(R.id.userIcon)
        val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
        val sentiScoreTextView = view.findViewById<TextView>(R.id.sentiScoreTextView)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        val activityIcon = view.findViewById<EmojiTextView>(R.id.activityIcon)
    }
}