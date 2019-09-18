package com.shojishunsuke.kibunnsns.presentation.secen.main.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.domain.model.Post
import com.shojishunsuke.kibunnsns.domain.use_case.DetailPostsFragmentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPostsFragmentViewModel(private val post: Post) : ViewModel() {
    private val useCase: DetailPostsFragmentUseCase = DetailPostsFragmentUseCase(post)

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

    fun onItemClicked(post: Post) {
        useCase.increaseView(post.postId)
    }

    fun getUserName(): String? = if (post.userName.isNotBlank()) post.userName else null

    fun getEmojiCode(): String = if (post.actID.isNotBlank()) post.actID else ""

    fun getContentText(): String = post.contentText

    private fun loadNextPosts() {
        GlobalScope.launch {
            val posts = useCase.loadPosts()
            launch(Dispatchers.Main) {
                _nextPosts.postValue(posts)
                isLoading = false
            }
        }
    }

    class DetailPostsFragmentViewModelFactory(private val post: Post) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailPostsFragmentViewModel(post) as T
        }
    }
}