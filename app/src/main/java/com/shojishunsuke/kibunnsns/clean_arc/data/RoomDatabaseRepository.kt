package com.shojishunsuke.kibunnsns.clean_arc.data

import com.shojishunsuke.kibunnsns.MainApplication
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.LocalPost
import com.shojishunsuke.kibunnsns.model.Post
import com.shojishunsuke.kibunnsns.model.User
//
//class RoomDatabaseRepository : DataBaseRepository {
//
//    private val dao = MainApplication.database.localDao()
//    private val userId = 0
//
//    suspend fun initializeUser() {
//        val user = User(userId)
//        dao.createUser(user)
//    }
//
//    suspend fun getUserName(): String {
//        return dao.findUser()[0].name
//    }
//
//    suspend fun editUserName(name: String) {
//        dao.updateUserName(name, userId)
//    }
//
//    suspend fun editIcon(iconInfo: String) {
//        dao.updateIcon(iconInfo, userId)
//    }
//
//    suspend fun editTheme(themeInfo: Int) {
//        dao.updateTheme(themeInfo, userId)
//    }
//
//
//    override suspend fun loadWholeCollection(): List<LocalPost> {
//        return dao.findAll()
//    }
//
//    override suspend fun loadDefaultCollection(previousPost: Post): List<Post> {
//
//    }
//
//    override suspend fun loadSortedNextCollection(basePost: Post, startPoint: Float, endPoint: Float): List<Post> {
//
//    }
//
//    override suspend fun savePost(post: Post) {
//        dao.createLocalPost(
//            post = LocalPost(
//                date = post.date,
//                contentText = post.contentText,
//                userName = "",
//                actId = post.actID
//            )
//        )
//    }
//}