package com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.domain.model.Post
import com.shojishunsuke.kibunnsns.presentation.custom_view.RecordPostCellView

class PostRecordRecyclerViewPagingAdapter(
    context: Context,
    private val removedListener: (Post) -> Unit
) : BasePagingAdapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_post_record_detail, parent, false) as RecordPostCellView
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = posts[position]

        holder as DetailViewHolder
        holder.view.build(post){
            removedListener(it)
            removeItem(position)
        }
    }

    private class DetailViewHolder(val view: RecordPostCellView) : RecyclerView.ViewHolder(view)
}