package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostItemUsecase
import com.shojishunsuke.kibunnsns.model.CloudPost

class PostItemViewModel:ViewModel() {
    private val usecase = PostItemUsecase()
    fun onItemClicked(cloudPost:CloudPost){
        usecase.increaseView(cloudPost.postId)
    }
}