package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.firebase.storage.StorageReference
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.domain.HomePostsFragmentUseCase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragmentViewModel:ViewModel() {
    private val useCase = HomePostsFragmentUseCase()
    private val _postsList = MutableLiveData<List<Post>>()

    val postsList:LiveData<List<Post>> get() = _postsList

    init {
        GlobalScope.launch {
            val wholePosts = useCase.loadWholePosts()

            launch(Dispatchers.IO ) {
                _postsList.postValue(wholePosts)
            }
        }
    }






}