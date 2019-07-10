package com.shojishunsuke.kibunnsns.adapter

import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.model.Post

abstract class PagingBaseAdapter<VH : RecyclerView.ViewHolder>(private val posts: MutableList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private fun add(post: Post) {
        val position = posts.size
        posts.add(position, post)
        notifyItemInserted(position)
    }

    fun addNextCollection(nextPosts: List<Post>) {
        nextPosts.forEach { add(it) }
    }

    override fun getItemCount(): Int = posts.size
}