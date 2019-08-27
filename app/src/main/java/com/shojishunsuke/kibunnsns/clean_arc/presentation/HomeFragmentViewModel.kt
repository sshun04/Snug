package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.HomePostsFragmentUseCase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class HomeFragmentViewModel : ViewModel() {
    private val useCase = HomePostsFragmentUseCase()
    private val _nextPosts = MutableLiveData<MutableList<Post>>()
    val nextPosts: LiveData<MutableList<Post>> get() = _nextPosts
    private var previousPost: Post? = null
    private var hideNegative = true
    var progressMood:Int = 0

    fun onScrollBottom() {
        requestPosts()
    }

//    private fun requestNextPosts() {
//        GlobalScope.launch {
//
//            val posts = useCase.requestPosts(hideNegative, previousPost?.date ?: Date())
//            if (posts.isNotEmpty()) previousPost = posts.last()
//            posts as MutableList<Post>
//            launch(Dispatchers.IO) {
//                _nextPosts.postValue(posts)
//            }
//        }
//    }

    fun onStopTracking(){
        refresh()
    }

    private fun requestPosts(){
        GlobalScope.launch {
            val posts = useCase.requestPostsByScore(progressMood,previousPost)
            if (posts.isNotEmpty()) previousPost = posts.last()
            posts as MutableList<Post>
            launch(Dispatchers.IO) {
                _nextPosts.postValue(posts)
            }
        }
    }




    fun refresh() {
        previousPost = null
        _nextPosts.value?.clear()
        requestPosts()
    }




}