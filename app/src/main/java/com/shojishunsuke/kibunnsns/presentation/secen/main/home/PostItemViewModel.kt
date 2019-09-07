package com.shojishunsuke.kibunnsns.presentation.secen.main.home

import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.domain.use_case.PostItemUseCase
import com.shojishunsuke.kibunnsns.domain.model.Post

class PostItemViewModel : ViewModel() {
    private val useCase: PostItemUseCase = PostItemUseCase()

    fun onItemClicked(post: Post) {
        useCase.increaseView(post.postId)
    }
}