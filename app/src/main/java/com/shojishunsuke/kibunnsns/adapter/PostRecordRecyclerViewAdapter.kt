package com.shojishunsuke.kibunnsns.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.utils.*
import java.util.*

class PostRecordRecyclerViewAdapter(context: Context) : PagingBaseAdapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val calendar = Calendar.getInstance()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = posts[position]
        calendar.time = post.date
        val detailDateString = calendar.detailDateString()
        val time = takeTimeFromDate(calendar.time)
        val activityIcon =
            if (post.actID.isNotBlank()) post.actID else getAppropriateIconFromSentiScore(post.sentiScore)

        if (viewType == 1) {
            holder as ViewHolder
            val day = calendar.dayOfWeek()
           val dayTextColor : Int =  when (day) {
                "土" -> Color.rgb(79,195 ,247 )
                "日" -> Color.argb(255,229, 115, 115)
                else -> Color.argb(138,0,0,0)
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

    }
}