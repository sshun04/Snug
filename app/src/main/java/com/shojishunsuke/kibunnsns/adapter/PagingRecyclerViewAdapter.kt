package com.shojishunsuke.kibunnsns.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.GlideApp
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.model.Post
import de.hdodenhof.circleimageview.CircleImageView

class PagingRecyclerViewAdapter(
    private val context: Context,
    private var postsList: MutableList<Post> = mutableListOf(),
    private val listener: (Post) -> Unit
) :
    PagingBaseAdapter<RecyclerView.ViewHolder>(postsList) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = postsList[position]
        if (viewType==1) {
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
                    .into(holder.userIcon)
            } else {
                holder.userIcon.setImageResource(R.color.colorPrimary)
            }

            holder.itemParent.setOnClickListener {
                listener(post)
            }
        }else {
            holder as LinearRecyclerViewHolder
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
                    .into(holder.userIcon)
            } else {
                holder.userIcon.setImageResource(R.color.colorPrimary)
            }

            holder.itemParent.setOnClickListener {
                listener(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            1 -> {
                val mView = inflater.inflate(R.layout.item_post, parent, false)
              return GridRecyclerViewHolder(mView)
            }

            2 -> {
                val mView = inflater.inflate(R.layout.item_post_linear,parent,false)
                return LinearRecyclerViewHolder(mView)
            }
            else ->{
                throw IllegalArgumentException()
            }
        }

    }
    inner class  LinearRecyclerViewHolder(view: View):RecyclerView.ViewHolder(view){
        val itemParent = view.findViewById<LinearLayout>(R.id.postBaseView)
        val userNameTextView = view.findViewById<TextView>(R.id.userName)
        val userIcon = view.findViewById<CircleImageView>(R.id.userIcon)
        val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        val activityIcon = view.findViewById<EmojiTextView>(R.id.activityIcon)
    }

    inner class GridRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemParent = view.findViewById<LinearLayout>(R.id.postBaseView)
        val userNameTextView = view.findViewById<TextView>(R.id.userName)
        val userIcon = view.findViewById<CircleImageView>(R.id.userIcon)
        val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        val activityIcon = view.findViewById<EmojiTextView>(R.id.activityIcon)
    }



}