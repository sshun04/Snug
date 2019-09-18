package com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.presentation.custom_view.EmojiCellView

class EmojiRecyclerViewAdapter(
    context: Context,
    private val unicodeList: MutableList<String> = mutableListOf(),
    private val emojiListener: (String) -> Unit
) : RecyclerView.Adapter<EmojiRecyclerViewAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_emoji, parent, false) as EmojiCellView
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val unicode = unicodeList[position]
        holder.view.build(unicode, emojiListener)
    }

    override fun getItemCount(): Int = unicodeList.count()

    fun setValue(list: List<String>) {
        unicodeList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: EmojiCellView) : RecyclerView.ViewHolder(view)
}