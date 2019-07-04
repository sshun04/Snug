package com.shojishunsuke.kibunnsns.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.model.LocalPost

class RecordRecyclerViewAdapter(private val context: Context, private val list: List<LocalPost>) :
    RecyclerView.Adapter<RecordRecyclerViewAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val localPost = list[position]

        holder.contentTextView.text = localPost.contentText
        holder.activityIcon.text = localPost.actId
        holder.dateTextView.text = localPost.date.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_post,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        val activityIcon = view.findViewById<EmojiTextView>(R.id.activityIcon)

    }
}