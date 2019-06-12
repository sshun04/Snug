package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.adapter.PostsRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.domain.HomePostsFragmentUseCase

class HomePostsFragmentViewModel:ViewModel() {
    private val useCase = HomePostsFragmentUseCase()

    fun setUpRecyclerViewAdapter(context: Context):PostsRecyclerViewAdapter{
        return PostsRecyclerViewAdapter(context,useCase.getPosts("sentiScore",0))
    }
}