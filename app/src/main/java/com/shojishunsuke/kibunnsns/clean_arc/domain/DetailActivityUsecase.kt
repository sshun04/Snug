package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.model.Post

class DetailActivityUsecase {
   private val dataBaseRepository = FireStoreDatabaseRepository()

   suspend fun loadRelatedPosts(post: Post):List<Post>{
       return dataBaseRepository.loadWholeCollection()
   }

}