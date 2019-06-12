package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post

class PostDialogUseCase {
   private val fireStoreDataBase = FireStoreDataBaseRepository()

    fun onPost(post:Post){
        fireStoreDataBase.savePost(post)
    }

}