package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostsRecordFragmentUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostsRecordFragmentViewModel : ViewModel() {
    private val usecase = PostsRecordFragmentUsecase()
    private val _postsList = MutableLiveData<List<Post>>()
    val postsList: LiveData<List<Post>> get() = _postsList

   private fun requirePosts() {
        GlobalScope.launch {
            val posts = usecase.loadPosts()
            launch(Dispatchers.IO) {
                _postsList.postValue(posts)
            }
        }
    }

    fun onPostRemoved(post: Post){

    }

    fun refresh(){
        requirePosts()
    }

}