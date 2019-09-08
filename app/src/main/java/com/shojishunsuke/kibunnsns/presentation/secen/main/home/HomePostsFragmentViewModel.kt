package com.shojishunsuke.kibunnsns.presentation.secen.main.home

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.domain.use_case.HomePostsFragmentUseCase
import com.shojishunsuke.kibunnsns.domain.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomePostsFragmentViewModel : ViewModel() {
    private val useCase = HomePostsFragmentUseCase()
    private val _nextPosts = MutableLiveData<MutableList<Post>>()
    val nextPosts: LiveData<MutableList<Post>> get() = _nextPosts
    var progressMood: Int = 10

    fun onScrollBottom() {
        requestPosts()
    }

    fun getProgressSeekBarColor(): Int {
        return when {
            progressMood >= 18 -> Color.rgb(255, 131, 130)
            progressMood in 13 until 18 -> Color.rgb(250, 171, 180)
            progressMood in 8..12 -> Color.rgb(158, 230, 160)
            progressMood in 3..7 -> Color.rgb(174, 244, 255)
            progressMood <= 2 -> Color.rgb(154, 204, 255)
            else -> Color.rgb(169, 255, 225)
        }
    }

    fun onStopTracking() {
        refresh()
    }

    fun onItemClicked(post: Post) {
        useCase.increaseView(post.postId)
    }

    private fun requestPosts() {
        GlobalScope.launch {
            val posts = useCase.requestPostsByScore(progressMood)
            posts as MutableList<Post>
            launch(Dispatchers.IO) {
                _nextPosts.postValue(posts)
            }
        }
    }

    fun refresh() {
        _nextPosts.value?.clear()
        useCase.resetValues()
        requestPosts()
    }
}