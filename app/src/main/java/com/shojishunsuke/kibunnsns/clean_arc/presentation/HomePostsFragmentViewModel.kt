package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.shojishunsuke.kibunnsns.adapter.PostsRecyclerViewAdapter
import com.shojishunsuke.kibunnsns.clean_arc.domain.HomePostsFragmentUseCase
import kotlinx.coroutines.runBlocking

class HomePostsFragmentViewModel:ViewModel() {
    private val useCase = HomePostsFragmentUseCase()
    lateinit var liveAdapter : MutableLiveData<PostsRecyclerViewAdapter>



   suspend fun setUpRecyclerViewAdapter(context: Context):PostsRecyclerViewAdapter = runBlocking{
      return@runBlocking PostsRecyclerViewAdapter(context,useCase.getPosts("sentiScore",0))
    }
}