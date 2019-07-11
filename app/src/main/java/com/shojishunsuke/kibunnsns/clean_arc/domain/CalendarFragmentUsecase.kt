package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import java.util.*

class CalendarFragmentUsecase {
    private val fireStoreRepository = FireStoreDatabaseRepository()

   suspend fun loadPostsByDate(date: String):List<Post>{
      return  fireStoreRepository.loadCollectionsByDate(date)
    }
}