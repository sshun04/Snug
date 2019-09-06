package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.PostRecordItemUsecase
import com.shojishunsuke.kibunnsns.model.CloudPost
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostRecordItemViewModel : ViewModel() {
    private val usecase = PostRecordItemUsecase()

    fun deletePost(cloudPost: CloudPost) {
        GlobalScope.launch {
            usecase.deletePostFromDatabase(cloudPost)
        }
    }
}