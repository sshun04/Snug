package com.shojishunsuke.kibunnsns.adapter.listener

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class EndlessScrollListener(
    private val layoutManager: StaggeredGridLayoutManager,
    private val onLoadMoreListener: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var loading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val lastVisiblePosition = layoutManager.findLastVisibleItemPositions(null).max()?.plus(2)

        if (!loading && lastVisiblePosition == totalItemCount) {

            loading = true
            recyclerView.post {
                onLoadMoreListener()
            }
        } else {
            loading = false
        }


    }

}