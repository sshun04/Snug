package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostDialogUseCase {
    private val fireStoreDataBase = FireStoreDataBaseRepository()

    fun onPost(post: Post) {
        GlobalScope.launch {
            fireStoreDataBase.savePost(post)
        }
    }

}