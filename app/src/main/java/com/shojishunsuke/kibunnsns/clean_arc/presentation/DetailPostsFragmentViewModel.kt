package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.util.Log
import androidx.lifecycle.LiveData
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
    private val useCase: DetailPostsFragmentUsecase = DetailPostsFragmentUsecase(post)
    private val _nextPosts = MutableLiveData<List<Post>>()

    val nextPosts: LiveData<List<Post>> get() = _nextPosts

    private var isLoading = true

    init {
        loadNextPosts()
    }

    fun onScrollBottom() {
        if (!isLoading) {
            isLoading = true
            loadNextPosts()
        }
    }

    private fun loadNextPosts() {
        GlobalScope.launch {
            val posts = useCase.loadPosts()
            launch(Dispatchers.Main) {
                _nextPosts.postValue(posts)
                isLoading = false
                Log.d("Load", "Finish")
            }
        }
    }

    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("YYYY年MM月dd日HH時mm分", Locale.JAPAN)
        return formatter.format(post.date)
    }

    private fun getAppropriateIconFromSentiScore(sentiScore: Float): String {
        return when {
            sentiScore > 0.4f -> "\uD83D\uDE01"
            sentiScore <= 0.4f && sentiScore >= -0.4f -> "\uD83D\uDE10"
            sentiScore < -0.4f -> "☹️"
            else -> "\uD83D\uDE10"
        }
    }

    fun getUserName(): String = if (post.userName.isNotBlank()) post.userName else "匿名"

    fun getEmojiCode(): String = if (post.actID.isNotBlank()) post.actID else ""

    fun getContentText(): String = post.contentText
}
