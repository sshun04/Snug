package com.shojishunsuke.kibunnsns.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PostRecordRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.PostsRecordFragmentViewModel
import kotlinx.android.synthetic.main.fragment_posts_record.view.*

class PostsRecordFragment : Fragment() {

    lateinit var recyclerViewAdapter: PostRecordRecyclerViewAdapter
    lateinit var viewModel: PostsRecordFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_posts_record, container, false)
     viewModel = ViewModelProviders.of(this).get(PostsRecordFragmentViewModel::class.java)

        recyclerViewAdapter = PostRecordRecyclerViewAdapter(requireContext()) {
            viewModel.onPostRemoved(it)
        }.apply {
            viewType = 1
        }

        val recyclerView = view.recentPostsRecyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            layoutAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.animation_recyclerview)
        }

        viewModel.postsList.observe(viewLifecycleOwner, Observer {
            recyclerViewAdapter.addNextCollection(it)
        })

        view.isDetailSwitch.setOnCheckedChangeListener { _, isDetail ->
            recyclerViewAdapter.viewType = if (isDetail) 2 else 1
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        recyclerViewAdapter.clear()
        viewModel.refresh()
    }
}