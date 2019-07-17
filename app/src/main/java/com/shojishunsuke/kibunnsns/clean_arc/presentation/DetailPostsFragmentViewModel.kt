package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.DetailPostsFragmentUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPostsFragmentViewModel(selectedPost: Post) : ViewModel() {
    private val useCase: DetailPostsFragmentUsecase
    val nextPosts = MutableLiveData<List<Post>>()
//    private var previousPost: Post

    init {
        useCase = DetailPostsFragmentUsecase(selectedPost)
        requestNextPosts()
    }

    fun requestNextPosts() {
        GlobalScope.launch {
            val posts = useCase.loadPosts()
            launch(Dispatchers.Main) {
                nextPosts.postValue(posts)
            }
        }
    }

}
