package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.RoomPostDateRepository
import com.shojishunsuke.kibunnsns.model.CloudPost

class PostRecordItemUsecase {
   private val fireStoreRepository = FireStoreDatabaseRepository()
    private val roomRepository = RoomPostDateRepository()

    fun deletePostFromDatabase(cloudPost:CloudPost){
        fireStoreRepository.deleteItemFromDatabase(cloudPost)
        roomRepository.deleteDate(cloudPost.date)
    }

}