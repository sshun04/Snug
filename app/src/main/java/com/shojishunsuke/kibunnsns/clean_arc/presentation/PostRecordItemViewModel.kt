package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostRecordItemUsecase
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostRecordItemViewModel : ViewModel() {
    private val usecase = PostRecordItemUsecase()

    fun deletePost(post: Post) {
        GlobalScope.launch {
            usecase.deletePostFromDatabase(post)
        }
    }
}