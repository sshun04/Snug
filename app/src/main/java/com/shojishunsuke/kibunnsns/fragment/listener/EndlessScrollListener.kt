package com.shojishunsuke.kibunnsns.fragment.listener

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
        val visiblePosition = layoutManager.findLastCompletelyVisibleItemPositions(null)
        val lastVisibleItemCount = visiblePosition.max()?.plus(1)

        if (!loading && lastVisibleItemCount == totalItemCount) {

            loading = true
            recyclerView.post {
                onLoadMoreListener()
            }
        } else {
            loading = false
        }
    }

}