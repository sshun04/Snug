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
    private var showNegativeOrNot = false

    init {
        requestFilteredPost(showNegativeOrNot)
    }

    fun onScrollBottom() {
        requestNextPosts()
    }

    private fun requestNextPosts() {
        GlobalScope.launch {
            val posts = useCase.load16items(previousPost)
            if (posts.isNotEmpty()) previousPost = posts.last()

            posts as MutableList<Post>
            launch(Dispatchers.IO) {
                nextPosts.postValue(posts)
            }
        }
    }

    fun onSortChanged(showNegative: Boolean, listener: () -> Unit) {
        listener()
        showNegativeOrNot = showNegative
        requestFilteredPost(showNegativeOrNot)
    }

    private fun requestFilteredPost(showNegative: Boolean) {
        GlobalScope.launch {
            val posts = useCase.loadFilteredPost(showNegative) as MutableList<Post>
            if (posts.isNotEmpty()) previousPost = posts.last()
            launch(Dispatchers.IO) { nextPosts.postValue(posts) }
        }

    }

    fun refresh() {
        previousPost = null
        nextPosts.value?.clear()
        requestFilteredPost(showNegativeOrNot)
    }
}