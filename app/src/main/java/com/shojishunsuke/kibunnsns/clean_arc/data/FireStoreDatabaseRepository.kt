package com.shojishunsuke.kibunnsns.clean_arc.data

import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class FireStoreDatabaseRepository : DataBaseRepository {

    private val dataBase = FirebaseFirestore.getInstance()

    companion object {
        private const val COLLECTION_PATH = "testPosts"
    }

    override suspend fun savePost(post: Post) {

        dataBase.collection(COLLECTION_PATH)
            .document()
            .set(post).await()

    }


   override suspend fun loadNextSortedCollection(basePost: Post, startPoint: Float, endPoint: Float): List<Post> {

        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .orderBy("sentiScore", Query.Direction.ASCENDING)
            .orderBy("date",Query.Direction.DESCENDING)
            .startAfter(basePost.sentiScore,basePost.date)
            .limit(12)
            .get()
            .await()

        val results = ArrayList<Post>()

        querySnapshot.forEach {
            val post = it.toObject(Post::class.java)
            results.add(post)
        }

        return results
    }

    override suspend fun loadFollowingCollection(previousPost: Post):List<Post>{
        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .orderBy("date",Query.Direction.DESCENDING)
            .startAfter(previousPost.date)
            .limit(16)
            .get()
            .await()

        val results = ArrayList<Post>()

        querySnapshot.forEach {
            val post = it.toObject(Post::class.java)
            results.add(post)
        }

        return results
    }

    suspend fun loadCollectionsByDate(date: Date):List<Post>{
        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .whereEqualTo("date",date)
            .get()
            .await()

        return queryToList(querySnapshot)
    }

    private suspend fun queryToList(querySnapshot: QuerySnapshot):List<Post>{
        val results = ArrayList<Post>()

        querySnapshot.forEach {
            val post = it.toObject(Post::class.java)
            results.add(post)
        }

        return results
    }
}