package com.shojishunsuke.kibunnsns.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
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

class HomePostsFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    lateinit var viewModel: HomeFragmentViewModel
    lateinit var pagingAdapter: PagingRecyclerViewAdapter
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_posts, container, false)

        viewModel =
            ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)


        val progressBar = view.progressBar.apply {
            max = 100
            setProgress(84, true)
        }
        Log.d("HomeFragment", "${this.tag}")

        view.homeToolBar.apply {
            title = "Snug"
            setTitleTextColor(resources.getColor(R.color.dark_54))
        }


        val stagLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        val scrollListener = EndlessScrollListener() {
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
            layoutAnimation =
                AnimationUtils.loadLayoutAnimation(this.context, R.anim.animation_recyclerview)
        }

        view.linear.setOnClickListener {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
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

        view.sentiSeekBar.apply {
            setOnSeekBarChangeListener(this@HomePostsFragment)
            progressDrawable?.setColorFilter(
                viewModel.getProgressSeekBarColor(),
                PorterDuff.Mode.SRC_ATOP
            )
            thumb?.setColorFilter(viewModel.getProgressSeekBarColor(), PorterDuff.Mode.SRC_IN)
            scrollBarSize = 8

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

    override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
        viewModel.progressMood = progress
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekbar: SeekBar?) {
        pagingAdapter.clear()
        viewModel.onStopTracking()
        val color = viewModel.getProgressSeekBarColor()
        seekbar?.progressDrawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        seekbar?.thumb?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    override fun onResume() {
        super.onResume()
        pagingAdapter.clear()
        viewModel.refresh()
        Log.d("HomeFragment", "onResume")
    }


    private fun setUpDetailFragment(post: Post) {
        DetailPostsFragment.setupFragment(post, requireFragmentManager())
    }

}