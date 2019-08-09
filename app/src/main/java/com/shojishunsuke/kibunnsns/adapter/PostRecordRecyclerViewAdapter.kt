package com.shojishunsuke.kibunnsns.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R

class PostRecordRecyclerViewAdapter(context: Context):PagingBaseAdapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val post = posts[position]
        holder.contentTextView.text = post.contentText
        holder.timeTextView.text = takeTimeFromDate(post.date)
        if (post.actID.isNotBlank()) {
            holder.activityIcon.text = post.actID
        } else {
            holder.activityIcon.text = getAppropriateIconFromSentiScore(post.sentiScore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_post_record,parent,false)
        return ViewHolder(view)
    }

   private class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val activityIcon: EmojiTextView = view.findViewById(R.id.activityIcon)
        val contentTextView: TextView = view.findViewById(R.id.contentTextView)
        val timeTextView: TextView = view.findViewById(R.id.dateTextView)
    }
}