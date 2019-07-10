package com.shojishunsuke.kibunnsns.clean_arc.data

import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

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


    private val baseQuery = dataBase.collection(COLLECTION_PATH)
//    private val secondQuery = dataBase.collection(COLLECTION_PATH)
//        .whereEqualTo("sentiScore",0.5)


    override suspend fun loadFilteredCollection(
        sentiScore: Float,
        magnitude: Float,
        keyWord: String,
        activityCode: String
    ): List<Post> = runBlocking {
        val results = ArrayList<Post>()


        val querySnapshot = dataBase.collection(COLLECTION_PATH)
//            .whereEqualTo("sentiScore",sentiScore)
            .whereEqualTo("actID", activityCode)
            .limit(20)
            .get()
            .addOnSuccessListener {

            }
            .await()



        for (result in querySnapshot) {
            val post = result.toObject(Post::class.java)
            results.add(post)
        }

        return@runBlocking results
    }

    override suspend fun loadWholeCollection(): List<Post> = runBlocking {

        val results = ArrayList<Post>()


        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .limit(10)
            .get().await()

        for (result in querySnapshot) {
            val post = result.toObject(Post::class.java)
            results.add(post)
        }

        return@runBlocking results
    }

    suspend fun loadNextCollection(basePost: Post, startPoint: Float, endPosint: Float): List<Post> {

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

    suspend fun loadFollowingCollection(previousPost: Post):List<Post>{
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


    fun loadPagingOptions(lifeCycleOwner: LifecycleOwner): FirestorePagingOptions<Post> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(5)
            .setPageSize(10)
            .build()

        val options = FirestorePagingOptions.Builder<Post>()
            .setQuery(baseQuery, config, Post::class.java)
            .setLifecycleOwner(lifeCycleOwner)
            .build()

        return options

    }


}