package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PostsRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.HomePostsFragmentViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.SharedViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomePostsFragment : Fragment() {

    lateinit var adapter: PostsRecyclerViewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_posts, container, false)

        val contentTextView = view.findViewById<TextView>(R.id.currentPostContent)
        val sentiScoreTextView = view.findViewById<TextView>(R.id.currentPostSentiScore)
        val dateTextView = view.findViewById<TextView>(R.id.currentPostDate)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar).apply {
            max = 100
            setProgress(84, true)

        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.postsRecyclerView).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            visibility = View.GONE
        }
        val homePostsFragmentViewModel = ViewModelProviders.of(this).get(HomePostsFragmentViewModel::class.java)

        val sharedViewModel = activity?.run {
            ViewModelProviders.of(this, SharedViewModelFactory(context!!)).get(PostsSharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")



        sharedViewModel.currentPosted.observe(this, Observer { currentPost ->

            contentTextView.text = currentPost.contentText
            sentiScoreTextView.text = currentPost.sentiScore.toString()
            dateTextView.text = currentPost.date.toString()
        })

        sharedViewModel.relatedPosts.observe(this, Observer { postsList ->

            recyclerView.adapter = PostsRecyclerViewAdapter(context!!, postsList)
            progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
        })

        return view
    }
}