package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostItemUsecase
import com.shojishunsuke.kibunnsns.model.Post

class PostItemViewModel:ViewModel() {
    private val usecase = PostItemUsecase()
    fun onItemClicked(post:Post){
        usecase.increaseView(post.postId)
    }
}