package com.shojishunsuke.kibunnsns.fragment

import android.content.res.Configuration
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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.adapter.PagingRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.presentation.HomeFragmentViewModel
import com.shojishunsuke.kibunnsns.fragment.listener.EndlessScrollListener
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.android.synthetic.main.fragment_home_posts.view.*

class HomePostsFragment : Fragment() {

    lateinit var viewModel: HomeFragmentViewModel
    lateinit var pagingAdapter: PagingRecyclerViewAdapter
    private var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_posts, container, false)

        viewModel =
            ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)


        val progressBar = view.progressBar.apply {
            max = 100
            setProgress(84, true)
        }

        view.homeToolBar.title = "KibunnSNS"

        val stagLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        val scrollListener = EndlessScrollListener(stagLayoutManager) {
            if (!isLoading) {
                isLoading = true
                viewModel.onScrollBottom()
            }
        }
        pagingAdapter = PagingRecyclerViewAdapter(requireContext()) {
            setUpDetailFragment(it)

        }
        val recyclerView = view.postsRecyclerView.apply {
            addOnScrollListener(scrollListener)
            adapter = pagingAdapter
            layoutManager = stagLayoutManager
            layoutAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.animation_recyclerview)
        }

        view.linear.setOnClickListener {
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            pagingAdapter.viewType = 2
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        }

        view.grid.setOnClickListener {
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            pagingAdapter.viewType = 1
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        }

        view.negativeSwitch.setOnCheckedChangeListener { _, boolean ->
            viewModel.onSortChanged(boolean) {
                pagingAdapter.clear()
            }
        }

        view.pullToRefreshLayout.setOnRefreshListener {
            pagingAdapter.clear()
            viewModel.refresh()
        }

        viewModel.nextPosts.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = View.GONE
            pagingAdapter.addNextCollection(it)
            isLoading = false
            view.pullToRefreshLayout.isRefreshing = false
        })

        return view
    }

    override fun onStart() {
        super.onStart()
        pagingAdapter.clear()
        viewModel.refresh()
    }


    private fun setUpDetailFragment(post: Post) {
        DetailPostsFragment.setupFragment(post, requireFragmentManager())
    }

}