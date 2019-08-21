package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostItemUsecase {
    private val fireStoreRepository = FireStoreDatabaseRepository()
    fun increaseView(postId:String){
        GlobalScope.launch { fireStoreRepository.icreaseViews(postId) }
    }
}