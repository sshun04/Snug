package com.shojishunsuke.kibunnsns.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel

class EmojiRecyclerViewAdapter(context: Context,private val sharedViewModel: PostsSharedViewModel,private val unicodeList:List<String>):
    RecyclerView.Adapter<EmojiRecyclerViewAdapter.ViewHolder>(){

    private val inflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val unicode = unicodeList[position]
        holder.emojiTextView.text = unicode
        holder.emojiTextView.setOnClickListener {
            sharedViewModel.emojiCode = unicode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_emoji,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = unicodeList.count()

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val emojiTextView = view.findViewById<EmojiTextView>(R.id.emojiTextView)
    }
}