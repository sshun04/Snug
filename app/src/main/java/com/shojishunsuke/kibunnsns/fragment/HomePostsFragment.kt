package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PostsRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.HomePostsFragmentViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.SharedViewModelFactory

class HomePostsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_posts, container, false)

        val sharedViewModel = activity?.run {
            ViewModelProviders.of(this, SharedViewModelFactory(context!!)).get(PostsSharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val fragmentViewModel = ViewModelProviders.of(this).get(HomePostsFragmentViewModel::class.java)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar).apply {
            max = 100
            setProgress(84, true)

        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.postsRecyclerView).apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            visibility = View.GONE
        }





        sharedViewModel.currentPosted.observe(this, Observer {


        })

        sharedViewModel.postsList.observe(this, Observer { postsList ->

            recyclerView.adapter = PostsRecyclerViewAdapter(requireContext(),fragmentViewModel, postsList)
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        })

        return view
    }
}