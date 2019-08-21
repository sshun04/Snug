package com.shojishunsuke.kibunnsns.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostRecordItemViewModel
import com.shojishunsuke.kibunnsns.model.Post
import com.shojishunsuke.kibunnsns.utils.dayOfMonth
import com.shojishunsuke.kibunnsns.utils.dayOfWeek
import com.shojishunsuke.kibunnsns.utils.detailDateString
import java.util.*

class PostRecordRecyclerViewAdapter(private val context: Context,private val removedListener:(Post)->Unit) : PagingBaseAdapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val calendar = Calendar.getInstance()

    private val viewModel = PostRecordItemViewModel()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = posts[position]
        calendar.time = post.date
        val detailDateString = calendar.detailDateString()
        val time = takeTimeFromDate(calendar.time)
        val activityIcon =
            if (post.actID.isNotBlank()) post.actID else getAppropriateIconFromSentiScore(post.sentiScore)

        calendar.time = post.date
        if (viewType == 1) {
            holder as ViewHolder
            val day = calendar.dayOfWeek()
            val dayTextColor: Int = when (day) {
                "土" -> Color.rgb(79, 195, 247)
                "日" -> Color.argb(255, 229, 115, 115)
                else -> Color.argb(138, 0, 0, 0)
            }

            holder.dayOfWeekTextView.apply {
                text = day
                setTextColor(dayTextColor)

            }
            holder.dayOfMonthTextView.apply {
                text = calendar.dayOfMonth().toString()
                setTextColor(dayTextColor)
            }
            holder.timeTextView1.text = time
            holder.timeTextView2.text = time
            holder.contentTextView.text = post.contentText
            holder.detailDateTextView.text = detailDateString
            holder.timeTextView2.text = takeTimeFromDate(post.date)
            holder.activityIcon.text = activityIcon
        } else {
            holder as DetailViewHolder
            holder.popMenuButton.setOnClickListener {
                val popupMenu = PopupMenu(context, it, Gravity.END)
                popupMenu.setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.deletePost -> {
                            val deleteDialog = AlertDialog.Builder(context)
                                .setPositiveButton("削除", DialogInterface.OnClickListener { _, _ ->

                                    viewModel.deletePost(post)
                                    removedListener(post)
                                    removeItem(position)
                                })
                                .setNegativeButton("キャンセル",null)
                                .setMessage("本当に削除しますか？")
                                .show()

                        }
                    }
                    return@setOnMenuItemClickListener true
                }
                popupMenu.inflate(R.menu.record_popup_menu)
                popupMenu.show()
            }

            holder.numberOfViews.text = post.views.toString()
            holder.detailDateTextView.text = detailDateString
            holder.timeTextView.text = time
            holder.contentTextView.text = post.contentText
            holder.activityICon.text = activityIcon
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val view = inflater.inflate(R.layout.item_post_record, parent, false)
            return ViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_post_record_detail, parent, false)
            return DetailViewHolder(view)
        }

    }

    private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val detailDateTextView: TextView = view.findViewById(R.id.detailDateTextView)
        val dayOfWeekTextView: TextView = view.findViewById(R.id.day)
        val dayOfMonthTextView: TextView = view.findViewById(R.id.dayOfMonth)
        val timeTextView1: TextView = view.findViewById(R.id.timeTextView1)
        val activityIcon: EmojiTextView = view.findViewById(R.id.activityIcon)
        val contentTextView: TextView = view.findViewById(R.id.contentTextView)
        val timeTextView2: TextView = view.findViewById(R.id.timeTextView2)
    }

    private class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val detailDateTextView: TextView = view.findViewById(R.id.detailDateTextView)
        val contentTextView: TextView = view.findViewById(R.id.contentTextView)
        val activityICon: TextView = view.findViewById(R.id.emojiIconTextView)
        val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        val popMenuButton: ImageButton = view.findViewById(R.id.popMenuButton)
        val numberOfViews :TextView = view.findViewById(R.id.numberOfViews)

    }

}