package com.shojishunsuke.kibunnsns.presentation.secen.main.record.my_post

import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.domain.use_case.PostRecordItemUseCase
import com.shojishunsuke.kibunnsns.domain.model.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostRecordItemViewModel : ViewModel() {
    private val usecase = PostRecordItemUseCase()

    fun deletePost(post: Post) {
        GlobalScope.launch {
            usecase.deletePostFromDatabase(post)
        }
    }
}