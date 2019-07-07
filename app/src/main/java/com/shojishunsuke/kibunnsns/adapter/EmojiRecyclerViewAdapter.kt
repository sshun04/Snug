package com.shojishunsuke.kibunnsns.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R

class EmojiRecyclerViewAdapter(
    context: Context,
    private val unicodeList: List<String>,
    private val emojiListener: (String) -> Unit
) :
    RecyclerView.Adapter<EmojiRecyclerViewAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val unicode = unicodeList[position]
        holder.emojiTextView.text = unicode
        holder.emojiTextView.setOnClickListener {
            emojiListener(unicode)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_emoji, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = unicodeList.count()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emojiTextView = view.findViewById<EmojiTextView>(R.id.emojiTextView)
    }
}