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

    val nextPosts: LiveData<List<Post>> get() = _nextPosts
    private val _nextPosts = MutableLiveData<List<Post>>()

    private var isLoading: Boolean = true

    init {
        loadNextPosts()
    }

    fun onScrollBottom() {
        if (!isLoading) {
            isLoading = true
            loadNextPosts()
        }
    }


    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("YYYY年MM月dd日HH時mm分", Locale.JAPAN)
        return formatter.format(post.date)
    }


    fun getUserName(): String = if (post.userName.isNotBlank()) post.userName else "匿名"

    fun getEmojiCode(): String = if (post.actID.isNotBlank()) post.actID else ""

    fun getContentText(): String = post.contentText

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

    private fun getAppropriateIconFromSentiScore(sentiScore: Float): String {
        return when {
            sentiScore > 0.4f -> "\uD83D\uDE01"
            sentiScore <= 0.4f && sentiScore >= -0.4f -> "\uD83D\uDE10"
            sentiScore < -0.4f -> "☹️"
            else -> "\uD83D\uDE10"
        }
    }
}
