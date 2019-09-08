package com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.domain.model.Post

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

    private fun add(post: Post) {
        val position = posts.size
        posts.add(position, post)
        notifyItemInserted(position)
    }
}