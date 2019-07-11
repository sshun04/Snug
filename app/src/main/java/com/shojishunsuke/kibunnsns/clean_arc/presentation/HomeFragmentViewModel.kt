package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.HomePostsFragmentUseCase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    private val useCase = HomePostsFragmentUseCase()
    val nextPosts = MutableLiveData<MutableList<Post>>()
    private var previousPost: Post? = null

    fun requestNextPosts() {
        GlobalScope.launch {
            val posts = useCase.load16items(previousPost).also {
                if (it.isNotEmpty()) previousPost = it.last()
            }
            posts as MutableList<Post>
            launch(Dispatchers.IO) {
                nextPosts.postValue(posts)
            }
        }
    }

    fun refresh() {
        previousPost = null
        requestNextPosts()
    }
}