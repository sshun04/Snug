package com.shojishunsuke.kibunnsns.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel
import com.shojishunsuke.kibunnsns.model.Post

class PostsRecyclerViewAdapter(
    private val context: Context,
    private val viewModel: PostsSharedViewModel,
    private var postsList: List<Post>
) :
    RecyclerView.Adapter<PostsRecyclerViewAdapter.PostsRecyclerViewHolder>() {


    override fun onBindViewHolder(holder: PostsRecyclerViewHolder, position: Int) {
        val post = postsList[position]
        holder.contentTextView.text = post.contentText
        holder.sentiScoreTextView.text = post.sentiScore.toString()
        holder.dateTextView.text = viewModel.getFormattedDate(post.date)
        holder.activityIcon.text = if (post.actID.isNotBlank())post.actID else ""

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsRecyclerViewHolder {
        val inflater = LayoutInflater.from(context)
        val mView = inflater.inflate(R.layout.item_post, parent, false)



        return PostsRecyclerViewHolder(mView)
    }

    override fun getItemCount(): Int = postsList.size


    inner class PostsRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemCardView = view.findViewById<CardView>(R.id.cardView)
        val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
        val sentiScoreTextView = view.findViewById<TextView>(R.id.sentiScoreTextView)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        val activityIcon = view.findViewById<EmojiTextView>(R.id.activityIcon)
    }
}