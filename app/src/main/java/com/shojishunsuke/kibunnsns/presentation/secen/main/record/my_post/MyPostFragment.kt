package com.shojishunsuke.kibunnsns.presentation.secen.main.record.my_post

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
import com.shojishunsuke.kibunnsns.presentation.recycler_view.adapter.PostRecordRecyclerViewPagingAdapter
import kotlinx.android.synthetic.main.fragment_posts_record.view.*

class MyPostFragment : Fragment() {
    lateinit var recyclerViewAdapter: PostRecordRecyclerViewPagingAdapter
    lateinit var viewModel: MyPostFragmentViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_posts_record, container, false)
        viewModel = ViewModelProviders.of(this).get(MyPostFragmentViewModel::class.java)

        recyclerViewAdapter = PostRecordRecyclerViewPagingAdapter(requireContext()) {post ->
            viewModel.onPostRemove(post)
        }

        view.recentPostsRecyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            layoutAnimation =
                    AnimationUtils.loadLayoutAnimation(this.context, R.anim.animation_recyclerview)
        }

        viewModel.postsList.observe(viewLifecycleOwner, Observer {
            recyclerViewAdapter.addNextCollection(it)
        })
        return view
    }

    override fun onResume() {
        super.onResume()
        recyclerViewAdapter.clear()
        viewModel.refresh()
    }
}