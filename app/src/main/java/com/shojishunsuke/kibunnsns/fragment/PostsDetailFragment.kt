package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.emoji.widget.EmojiTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PostsHomeRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsFragmentsViewModel
import com.shojishunsuke.kibunnsns.model.Post
import de.hdodenhof.circleimageview.CircleImageView

class PostsDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_posts_detail, container, false)

        val viewModel = requireActivity().run {
            ViewModelProviders.of(this).get(PostsFragmentsViewModel::class.java)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.relatedPostsRecyclerView).apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            layoutAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.animation_recyclerview)

        }
        val seletedPost = viewModel.selectedPost
        val userIcon = view.findViewById<CircleImageView>(R.id.headerUserIcon).apply {
            val iconUrl = seletedPost.iconPhotoLink
            Glide.with(requireContext())
                .load(viewModel.getIconRef(iconUrl))
                .error(R.drawable.ic_access_time_black_12dp)
                .into(this)
        }
        val userNameTextView = view.findViewById<TextView>(R.id.headerUserName).apply { text = seletedPost.userName }
        val actIcon = view.findViewById<EmojiTextView>(R.id.headerActIcon).apply { text = seletedPost.actID }
        val contentTextView = view.findViewById<TextView>(R.id.headerContentText).apply { text = seletedPost.contentText }
        val dateTextView = view.findViewById<TextView>(R.id.headerDate).apply { text = seletedPost.date.toString() }



        viewModel.relatedPosts.observe(this, Observer {
            recyclerView.adapter = PostsHomeRecyclerViewAdapter(requireContext(), viewModel, it,false) {

            }
        })

        return view
    }
}