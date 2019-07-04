package com.shojishunsuke.kibunnsns.clean_arc.data

import com.google.firebase.firestore.FirebaseFirestore
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FireStoreDatabaseRepository : DataBaseRepository {

    private val dataBase = FirebaseFirestore.getInstance()

    override suspend fun savePost(post: Post) {

        dataBase.collection("testPosts")
            .document()
            .set(post).await()

    }

    override suspend fun loadFilteredCollection(
        sentiScore: Float,
        magnitude: Float,
        keyWord: String,
        activityCode: String
    ): List<Post> = runBlocking {
        val results = ArrayList<Post>()


        val querySnapshot = dataBase.collection("testPosts")
//            .whereEqualTo("sentiScore",sentiScore)
            .whereEqualTo("actID",activityCode)
            .limit(1)
            .get()
            .await()

        for (result in querySnapshot) {
            val post = result.toObject(Post::class.java)
            results.add(post)
        }

        return@runBlocking results
    }

    override suspend fun loadWholeCollection(): List<Post> = runBlocking {

        val results = ArrayList<Post>()


        val querySnapshot = dataBase.collection("testPosts")
            .get().await()

        for (result in querySnapshot) {
            val post = result.toObject(Post::class.java)
            results.add(post)
        }

        return@runBlocking results
    }


}