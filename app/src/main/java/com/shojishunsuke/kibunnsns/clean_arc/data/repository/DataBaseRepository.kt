package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import com.shojishunsuke.kibunnsns.model.Post

interface DataBaseRepository {
    fun savePost(post: Post)
  suspend fun getFilteredCollection(fieldName:String,params:Any):List<Post>
}
