package com.shojishunsuke.kibunnsns.fragment.listener

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class EndlessScrollListener(private val onLoadMoreListener: () -> Unit) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val layoutManager = recyclerView.layoutManager
            val totalItemCount = layoutManager?.itemCount

            if (layoutManager is StaggeredGridLayoutManager) {

                val visiblePosition = layoutManager.findLastCompletelyVisibleItemPositions(null)
                val lastVisibleItemCount = visiblePosition.max()?.plus(1)

                if (lastVisibleItemCount == totalItemCount) {
                    onLoadMoreListener()
                    Log.d("EndlessScrollListener", "scrolled")
                }
            } else if (layoutManager is LinearLayoutManager) {
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemCount = layoutManager.findLastVisibleItemPosition().plus(1)

                if (lastVisibleItemCount == totalItemCount) {
                    onLoadMoreListener()
                    Log.d("EndlessScrollListener", "scrolled")
                }
            }
        }
    }
}