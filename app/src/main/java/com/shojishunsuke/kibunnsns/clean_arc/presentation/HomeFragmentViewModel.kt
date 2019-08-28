package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.graphics.Color
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
      var progressMood:Int = 0

    fun onScrollBottom() {
        requestPosts()
    }

    fun getProgressSeekBarColor():Int{
        return when{
            progressMood >= 8 ->  Color.rgb(250, 180, 185)
            progressMood in 3 until 8 ->Color.rgb(250, 210, 218)
            progressMood in -2 .. 2 -> Color.rgb(149, 255, 225)
            progressMood in - 7 ..-3  -> Color.rgb(150, 220, 255)
            progressMood <= -8  ->   Color.rgb(130, 200, 255)
            else ->    Color.rgb(169, 255, 225)
        }
    }

    fun onStopTracking(){
        refresh()
    }

    private fun requestPosts(){
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