package com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.domain.model.Post
import com.shojishunsuke.kibunnsns.presentation.custom_view.RecordPostCellView
import com.shojishunsuke.kibunnsns.presentation.custom_view.VerticalPostCellView
import com.shojishunsuke.kibunnsns.presentation.secen.main.record.my_post.PostRecordItemViewModel
import java.util.*

class PostRecordRecyclerViewPagingAdapter(
    context: Context,
    private val removedListener: (Post) -> Unit
) : BasePagingAdapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val calendar: Calendar = Calendar.getInstance()
    private val viewModel: PostRecordItemViewModel = PostRecordItemViewModel()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = posts[position]
//        calendar.time = post.date
//        val detailDateString = calendar.detailDateString()
//        val time = takeTimeFromDate(calendar.time)
//        val activityIcon =
//                if (post.actID.isNotBlank()) post.actID else ""
//
//        holder as DetailViewHolder
//        holder.popMenuButton.setOnClickListener {
//            val popupMenu = PopupMenu(context, it, Gravity.END)
//            popupMenu.setOnMenuItemClickListener { menu ->
//                when (menu.itemId) {
//                    R.id.deletePost -> {
//                        AlertDialog.Builder(context)
//                                .setPositiveButton("削除") { _, _ ->
//                                    viewModel.deletePost(post)
//                                    removedListener(post)
//                                    removeItem(position)
//                                }
//                                .setNegativeButton("キャンセル", null)
//                                .setMessage("本当に削除しますか？")
//                                .show()
//                    }
//                }
//                return@setOnMenuItemClickListener true
//            }
//            popupMenu.inflate(R.menu.record_popup_menu)
//            popupMenu.show()
//        }
//
//        holder.sentiScoreDescription.apply {
//            val description = getSentiDescription(post.sentiScore)
//            text = description.first
//            setBackgroundResource(description.second)
//        }
//
//        holder.numberOfViews.text = post.views.toString()
//        holder.detailDateTextView.text = detailDateString
//        holder.timeTextView.text = time
//        holder.contentTextView.text = post.contentText
//        holder.activityICon.text = activityIcon
        holder as DetailViewHolder
        holder.view.build(post){
            removedListener(it)
            removeItem(position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_post_record_detail, parent, false) as RecordPostCellView
        return DetailViewHolder(view)
    }

    private class DetailViewHolder(val view: RecordPostCellView) : RecyclerView.ViewHolder(view)
}