package com.shojishunsuke.kibunnsns.presentation.recycler_view.listener

import android.util.Log
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class NestedEndlessScrollListener(
        private val layoutManager: StaggeredGridLayoutManager,
        private val recyclerView: RecyclerView,
        private val onLoadMoreListener: () -> Unit
) : NestedScrollView.OnScrollChangeListener {
    override fun onScrollChange(
            v: NestedScrollView?,
            scrollX: Int,
            scrollY: Int,
            oldScrollX: Int,
            oldScrollY: Int
    ) {
        val totalItemCount = layoutManager.itemCount
        val visiblePosition = layoutManager.findLastCompletelyVisibleItemPositions(null)
        val lastVisibleItemCount = visiblePosition.max()?.plus(1)

        val view = v?.getChildAt(v.childCount.minus(1))
        val diff = view?.bottom?.minus(v.height + v.scrollY)

        if (diff == 0) {

            if (lastVisibleItemCount == totalItemCount) {
                Log.d("scrolling", "Load!!")
                recyclerView.post {
                    onLoadMoreListener()
                }
            }
        }
    }
}