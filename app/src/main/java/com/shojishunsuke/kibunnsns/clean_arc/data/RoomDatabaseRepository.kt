package com.shojishunsuke.kibunnsns.clean_arc.data

import com.shojishunsuke.kibunnsns.MainApplication
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.LocalPost
import com.shojishunsuke.kibunnsns.model.Post

class RoomDatabaseRepository : DataBaseRepository {

   private val dao = MainApplication.database.localDao()

    override suspend fun loadFilteredCollection(fieldName: String, params: Any): List<LocalPost> {
        return dao.findAll()
    }

    override suspend fun loadWholeCollection(): List<LocalPost> {
        return dao.findAll()
    }

    override suspend fun savePost(post: Post) {
        dao.createLocalPost(post = LocalPost(date = post.date,contentText = post.contentText,userName = "",actId = post.actID))
    }
}