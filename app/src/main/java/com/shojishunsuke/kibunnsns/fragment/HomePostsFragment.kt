package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.CustomPagingAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.HomeFragmentViewModel
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.android.synthetic.main.fragment_home_posts.*
import kotlinx.android.synthetic.main.fragment_home_posts.view.*
import kotlinx.android.synthetic.main.fragment_home_posts.view.postsRecyclerView

class HomePostsFragment : Fragment() {


    lateinit var pagingAdapter: CustomPagingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_posts, container, false)

        val fragmentViewModel =
            requireActivity().run { ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java) }

        val progressBar = view.progressBar.apply {
            max = 100
            setProgress(84, true)
        }

        val recyclerView = view.postsRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            layoutAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.animation_recyclerview)
        }

        view.linear.setOnClickListener {
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        }

        view.grid.setOnClickListener {
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        }

        val pagingOptions = fragmentViewModel.requestPagingOptionBuilder(requireActivity())

        pagingAdapter =
            CustomPagingAdapter(requireContext(), pagingOptions, {
                recyclerView.scheduleLayoutAnimation()
                progressBar.visibility = View.GONE
            }) {
                setUpDetailFragment(it)
            }

        recyclerView.adapter = pagingAdapter

        return view
    }



    override fun onStart() {
        super.onStart()
        pagingAdapter.startListening()

    }

    override fun onDestroy() {
        pagingAdapter.stopListening()
        super.onDestroy()

    }


    private fun setUpDetailFragment(post: Post) {
        DetaiPostslFragment.setupFragment(post, requireFragmentManager())
    }

}