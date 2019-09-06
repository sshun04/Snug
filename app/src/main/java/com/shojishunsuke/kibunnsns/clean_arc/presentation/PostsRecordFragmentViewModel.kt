package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostsRecordFragmentUsecase
import com.shojishunsuke.kibunnsns.model.CloudPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostsRecordFragmentViewModel : ViewModel() {
    private val usecase = PostsRecordFragmentUsecase()
    private val _postsList = MutableLiveData<List<CloudPost>>()
    val postsList: LiveData<List<CloudPost>> get() = _postsList

   private fun requestPosts() {
        GlobalScope.launch {
            val posts = usecase.loadPosts()
            launch(Dispatchers.IO) {
                _postsList.postValue(posts)
            }
        }
    }

    fun refresh(){
        requestPosts()
    }

}