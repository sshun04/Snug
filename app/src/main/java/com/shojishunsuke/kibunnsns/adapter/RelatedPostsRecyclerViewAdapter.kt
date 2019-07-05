import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.GlideApp
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsFragmentsViewModel
import com.shojishunsuke.kibunnsns.model.Post
import de.hdodenhof.circleimageview.CircleImageView

//package com.shojishunsuke.kibunnsns.adapter
//
class RelatedPostsRecyclerViewAdapter(
    private val context: Context,
    private val fragmentViewModel: PostsFragmentsViewModel,
    private val headerPost: Post,
    relatedPosts: List<Post>
) :
    RecyclerView.Adapter<RelatedPostsRecyclerViewAdapter.ViewHolder>() {


    companion object {
        private val VIEW_TYPE_HEADER = 1
        private val VIEW_TYPE_CONTENTS = 2
    }

    private val inflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userNameTextView.text = if (headerPost.userName.isNotBlank()) headerPost.userName else "匿名"
        holder.contentTextView.text = headerPost.contentText
        holder.sentiScoreTextView.text = headerPost.sentiScore.toString()
        holder.dateTextView.text = headerPost.date.toString()
        holder.activityIcon.text =
            if (headerPost.actID.isNotBlank()) headerPost.actID else fragmentViewModel.getAppropriateIcon(headerPost.sentiScore)

        if (headerPost.iconPhotoLink.isNotBlank()) {
            GlideApp.with(context)
                .load(fragmentViewModel.getIconRef(headerPost.iconPhotoLink))
                .into(holder.userIcon)
        } else {
            holder.userIcon.setImageResource(R.color.colorPrimary)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_post_linear, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userNameTextView = view.findViewById<TextView>(R.id.userName)
        val userIcon = view.findViewById<CircleImageView>(R.id.userIcon)
        val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
        val sentiScoreTextView = view.findViewById<TextView>(R.id.sentiScoreTextView)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        val activityIcon = view.findViewById<EmojiTextView>(R.id.activityIcon)
    }
}