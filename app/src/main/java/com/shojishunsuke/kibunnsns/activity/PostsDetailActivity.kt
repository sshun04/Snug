package com.shojishunsuke.kibunnsns.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PostsRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostDetailActivityViewModel
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.android.synthetic.main.activity_posts_detail.*

class PostsDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_POST = "post"

        fun start(context: Context, post: Post) {
            context.startActivity(createIntent(context).apply {
                putExtra(EXTRA_POST, post)
            })
        }

        private fun createIntent(context: Context): Intent {
            return Intent(context, PostsDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_detail)
        val currentPosted = intent.getSerializableExtra(EXTRA_POST) as Post

        val viewModel = run {
            ViewModelProviders.of(this).get(PostDetailActivityViewModel::class.java)
        }
        viewModel.requestRelatedPosts(currentPosted)

        detailRelatedPostsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        headerActIcon.text = currentPosted.actID
        Glide.with(this)
            .load(currentPosted.iconPhotoLink)
            .into(headerUserIcon)

        headerContentText.text = currentPosted.contentText
        headerDate.text = currentPosted.date.toString()

        viewModel.relatedPosts.observe(this, Observer { relatedPosts ->
            detailRelatedPostsRecyclerView.adapter = PostsRecyclerViewAdapter(this, relatedPosts) {
                start(this, it)
            }
        })


    }
}
