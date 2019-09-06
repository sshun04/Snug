package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.DetailPostsFragmentUsecase
import com.shojishunsuke.kibunnsns.model.CloudPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DetailPostsFragmentViewModel(private val cloudPost: CloudPost) : ViewModel() {
    private val useCase: DetailPostsFragmentUsecase = DetailPostsFragmentUsecase(cloudPost)

    private val _nextPosts = MutableLiveData<List<CloudPost>>()
    val nextPosts: LiveData<List<CloudPost>> get() = _nextPosts

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
        return formatter.format(cloudPost.date)
    }

    private fun getAppropriateIconFromSentiScore(sentiScore: Float): String {
        return when {
            sentiScore > 0.4f -> "\uD83D\uDE01"
            sentiScore <= 0.4f && sentiScore >= -0.4f -> "\uD83D\uDE10"
            sentiScore < -0.4f -> "☹️"
            else -> "\uD83D\uDE10"
        }
    }

    fun getUserName(): String = if (cloudPost.userName.isNotBlank()) cloudPost.userName else "匿名"

    fun getEmojiCode(): String = if (cloudPost.actID.isNotBlank()) cloudPost.actID else ""

    fun getContentText(): String = cloudPost.contentText

}
