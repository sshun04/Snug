package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.shojishunsuke.kibunnsns.clean_arc.data.EmotionAnalysisRepository
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostsSharedUseCase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostsSharedViewModel(context: Context) : ViewModel() {
  private  val _currentPosted = MutableLiveData<Post>()
 private   val _relatedPosts = MutableLiveData<List<Post>>()
    private val useCase: PostsSharedUseCase = PostsSharedUseCase(EmotionAnalysisRepository(context))

    val currentPosted: LiveData<Post> get() = _currentPosted
    val relatedPosts: LiveData<List<Post>> get() = _relatedPosts

    fun onPost(content: String) {
        GlobalScope.launch {

            val post = useCase.generatePost(content)
            val relatedPosts = useCase.loadRelatedPosts(post)

            launch(Dispatchers.IO) {

                _currentPosted.postValue(post)
                _relatedPosts.postValue(relatedPosts)
            }

        }
    }
}