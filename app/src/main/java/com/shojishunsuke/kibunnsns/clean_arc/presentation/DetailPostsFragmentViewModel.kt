package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.shojishunsuke.kibunnsns.clean_arc.domain.DetailPostsFragmentUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPostsFragmentViewModel : ViewModel() {
    private val useCase = DetailPostsFragmentUsecase()
    val nextPosts = MutableLiveData<List<Post>>()
    private var previousPost: Post? = null

    fun requestNextPosts(selectedPost: Post) {
        GlobalScope.launch {
            val posts = useCase.loadNextRelatedPosts(selectedPost, previousPost)
            if (posts.isNotEmpty())previousPost = posts.last()

            launch(Dispatchers.IO) {
                nextPosts.postValue(posts)
            }
        }
    }
}