package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.DetailActivityUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostDetailActivityViewModel : ViewModel() {
    private val useCase = DetailActivityUsecase()
    private val _relatedPosts = MutableLiveData<List<Post>>()
    val relatedPosts:LiveData<List<Post>> get() = _relatedPosts

    fun requestRelatedPosts(post:Post){
        GlobalScope.launch {
            val posts = useCase.loadRelatedPosts(post)

            launch(Dispatchers.IO) {
                _relatedPosts.postValue(posts)
            }
        }
    }

}