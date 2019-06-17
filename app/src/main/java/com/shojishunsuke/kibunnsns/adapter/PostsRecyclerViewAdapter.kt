package com.shojishunsuke.kibunnsns.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel
import com.shojishunsuke.kibunnsns.model.Post

class PostsRecyclerViewAdapter(private val context: Context,private val viewModel:PostsSharedViewModel, private val postsList: List<Post>) :
    RecyclerView.Adapter<PostsRecyclerViewAdapter.PostsRecyclerViewHolder>() {


    override fun onBindViewHolder(holder: PostsRecyclerViewHolder, position: Int) {
        val post = postsList[position]
        holder.contentTextView.text = post.contentText
        holder.sentiScoreTextView.text = post.sentiScore.toString()
        holder.dateTextView.text = post.date.toString()

        holder.contentTextView.setOnClickListener {
            viewModel.onPostSelected(post)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsRecyclerViewHolder {
        val inflater = LayoutInflater.from(context)
        val mView = inflater.inflate(R.layout.item_post, parent, false)



        return PostsRecyclerViewHolder(mView)
    }


    override fun getItemCount(): Int = postsList.size


    inner class PostsRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentTextView  = view.findViewById<TextView>(R.id.contentTextView)
        val sentiScoreTextView = view.findViewById<TextView>(R.id.sentiScoreTextView)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
    }
}