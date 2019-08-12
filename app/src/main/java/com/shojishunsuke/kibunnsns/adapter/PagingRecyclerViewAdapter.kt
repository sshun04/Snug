package com.shojishunsuke.kibunnsns.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.GlideApp
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.model.Post
import de.hdodenhof.circleimageview.CircleImageView

class PagingRecyclerViewAdapter(
    private val context: Context,
    private val listener: (Post) -> Unit
) :
    PagingBaseAdapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = posts[position]
        when (viewType) {
            1 -> {
                holder as GridRecyclerViewHolder
                holder.userNameTextView.text = if (post.userName.isNotBlank()) post.userName else "匿名"
                holder.contentTextView.text = post.contentText
                holder.dateTextView.text = formatDate(post.date)

                if (post.actID.isNotBlank()) {
                    holder.activityIcon.visibility = View.VISIBLE
                    holder.activityIcon.text = post.actID
                } else {
                    holder.activityIcon.visibility = View.GONE
                }

                if (post.iconPhotoLink.isNotBlank()) {
                    GlideApp.with(context)
                        .load(post.iconPhotoLink)
                        .placeholder(R.drawable.usericon_default)
                        .error(R.drawable.usericon_default)
                        .into(holder.userIcon)
                } else {
                    holder.userIcon.setImageResource(R.color.colorPrimary)
                }

                holder.itemParent.setOnClickListener {
                    listener(post)
                }
            }

            2 -> {
                holder as VerticalRecyclerViewHolder
                holder.userNameTextView.text = if (post.userName.isNotBlank()) post.userName else "匿名"
                holder.contentTextView.text = post.contentText
                holder.dateTextView.text = formatDate(post.date)


                holder.activityIcon.text =
                    if (post.actID.isNotBlank()) post.actID else getAppropriateIconFromSentiScore(post.sentiScore)



                if (post.iconPhotoLink.isNotBlank()) {
                    GlideApp.with(context)
                        .load(post.iconPhotoLink)
                        .error(R.drawable.usericon_default)
                        .placeholder(R.drawable.usericon_default)
                        .into(holder.userIcon)
                } else {
                    holder.userIcon.setImageResource(R.color.colorPrimary)
                }

                holder.itemParent.setOnClickListener {
                    listener(post)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        when (viewType) {
            1 -> {
                val mView = inflater.inflate(R.layout.item_post, parent, false)
                return GridRecyclerViewHolder(mView)
            }
            2 -> {
                val mView = inflater.inflate(R.layout.item_post_vertical, parent, false)
                return VerticalRecyclerViewHolder(mView)
            }
            else -> {
                throw IllegalArgumentException()
            }


        }
    }

    inner class VerticalRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemParent: ConstraintLayout = view.findViewById(R.id.postBaseView)
        val userNameTextView: TextView = view.findViewById(R.id.userName)
        val userIcon: CircleImageView = view.findViewById(R.id.userIcon)
        val contentTextView: TextView = view.findViewById(R.id.contentTextView)
        val dateTextView: TextView = view.findViewById(R.id.timeTextView2)
        val activityIcon: EmojiTextView = view.findViewById(R.id.activityIcon)
    }

    inner class GridRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemParent: LinearLayout = view.findViewById(R.id.postBaseView)
        val userNameTextView: TextView = view.findViewById(R.id.userName)
        val userIcon: CircleImageView = view.findViewById(R.id.userIcon)
        val contentTextView: TextView = view.findViewById(R.id.contentTextView)
        val dateTextView: TextView = view.findViewById(R.id.timeTextView2)
        val activityIcon: EmojiTextView = view.findViewById(R.id.activityIcon)
    }


}