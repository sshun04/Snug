package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.DetailPostsFragmentUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DetailPostsFragmentViewModel(private val post: Post) : ViewModel() {
    private val useCase: DetailPostsFragmentUsecase
    val nextPosts = MutableLiveData<List<Post>>()

    private var isLoading = true

    init {
        useCase = DetailPostsFragmentUsecase(post)
        loadNextPosts()
    }

    fun onScrollBottom(){
        if (!isLoading) {
            isLoading = true
            loadNextPosts()
        }
    }

    private fun loadNextPosts() {
        GlobalScope.launch {
            val posts = useCase.loadPosts()
            launch(Dispatchers.Main) {
                nextPosts.postValue(posts)
                isLoading = false
                Log.d("Load", "Finish")
            }
        }
    }

    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("YYYY年MM月dd日HH時mm分", Locale.JAPAN)
        return formatter.format(post.date)
    }

    fun getUserName(): String = if (post.userName.isNotBlank()) post.userName else "匿名"

    fun getEmojiCode(): String = if (post.actID.isNotBlank()) post.actID else "\uD83D\uDE42"

    fun getContentText(): String = post.contentText

}
