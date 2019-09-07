package com.shojishunsuke.kibunnsns.presentation.secen.main.record.my_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.domain.use_case.PostsRecordFragmentUseCase
import com.shojishunsuke.kibunnsns.domain.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyPostFragmentViewModel : ViewModel() {
    private val usecase = PostsRecordFragmentUseCase()

    val postsList: LiveData<List<Post>> get() = _postsList
    private val _postsList = MutableLiveData<List<Post>>()

    fun refresh() {
        requestPosts()
    }

    private fun requestPosts() {
        GlobalScope.launch {
            val posts = usecase.loadPosts()
            launch(Dispatchers.IO) {
                _postsList.postValue(posts)
            }
        }
    }
}