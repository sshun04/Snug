package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PostsHomeRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsFragmentsViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsSharedViewModel
import com.shojishunsuke.kibunnsns.clean_arc.presentation.factory.SharedViewModelFactory

class HomePostsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_posts, container, false)

        val fragmentViewModel = requireActivity().run {  ViewModelProviders.of(this).get(PostsFragmentsViewModel::class.java)}
        val sharedViewModel = requireActivity().run {
            ViewModelProviders.of(this, SharedViewModelFactory(context!!)).get(PostsSharedViewModel::class.java)
        }


        val linearButton = view.findViewById<ImageView>(R.id.linear)
        val gridButton  = view.findViewById<ImageView>(R.id.grid)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar).apply {
            max = 100
            setProgress(84, true)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.postsRecyclerView).apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            layoutAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.animation_recyclerview)
//                LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            visibility = View.GONE
        }



        linearButton.setOnClickListener {
            recyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        }

        gridButton.setOnClickListener {
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        }


        sharedViewModel.postsList.observe(this, Observer { postsList ->

            recyclerView.adapter = PostsHomeRecyclerViewAdapter(requireContext(),fragmentViewModel, postsList,true)
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
//                LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        })



        return view
    }
}