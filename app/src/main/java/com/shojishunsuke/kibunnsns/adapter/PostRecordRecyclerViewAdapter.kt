package com.shojishunsuke.kibunnsns.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostRecordItemViewModel
import com.shojishunsuke.kibunnsns.model.Post
import com.shojishunsuke.kibunnsns.utils.detailDateString
import java.util.*

class PostRecordRecyclerViewAdapter(
        private val context: Context,
        private val removedListener: (Post) -> Unit
) : PagingBaseAdapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val calendar: Calendar = Calendar.getInstance()
    private val viewModel: PostRecordItemViewModel = PostRecordItemViewModel()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = posts[position]
        calendar.time = post.date
        val detailDateString = calendar.detailDateString()
        val time = takeTimeFromDate(calendar.time)
        val activityIcon =
                if (post.actID.isNotBlank()) post.actID else ""

        calendar.time = post.date

        holder as DetailViewHolder
        holder.popMenuButton.setOnClickListener {
            val popupMenu = PopupMenu(context, it, Gravity.END)
            popupMenu.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.deletePost -> {
                        AlertDialog.Builder(context)
                                .setPositiveButton("削除") { _, _ ->
                                    viewModel.deletePost(post)
                                    removedListener(post)
                                    removeItem(position)
                                }
                                .setNegativeButton("キャンセル", null)
                                .setMessage("本当に削除しますか？")
                                .show()
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.inflate(R.menu.record_popup_menu)
            popupMenu.show()
        }

        holder.sentiScoreDescription.apply {
            val description = getSentiDescription(post.sentiScore)
            text = description.first
            setBackgroundResource(description.second)
        }

        holder.numberOfViews.text = post.views.toString()
        holder.detailDateTextView.text = detailDateString
        holder.timeTextView.text = time
        holder.contentTextView.text = post.contentText
        holder.activityICon.text = activityIcon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_post_record_detail, parent, false)
        return DetailViewHolder(view)
    }

    private class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sentiScoreDescription: TextView = view.findViewById(R.id.sentiDescription)
        val detailDateTextView: TextView = view.findViewById(R.id.detailDateTextView)
        val contentTextView: TextView = view.findViewById(R.id.contentTextView)
        val activityICon: TextView = view.findViewById(R.id.emojiIconTextView)
        val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        val popMenuButton: ImageButton = view.findViewById(R.id.popMenuButton)
        val numberOfViews: TextView = view.findViewById(R.id.numberOfViews)
    }
}