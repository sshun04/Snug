package com.shojishunsuke.kibunnsns.clean_arc.data

import android.util.Log
import com.google.firebase.firestore.*
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class FireStoreDatabaseRepository : DataBaseRepository {

    private val dataBase = FirebaseFirestore.getInstance()

    init {
        dataBase.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }


    companion object {
        private const val COLLECTION_PATH = "betaTest"
    }

    override suspend fun savePost(post: Post) {

        dataBase.collection(COLLECTION_PATH)
            .document(post.postId)
            .set(post).await()

    }


    override suspend fun loadSortedNextCollection(basePost: Post): List<Post> = runBlocking {

        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .whereEqualTo("actID", basePost.actID)
            .orderBy("sentiScore", Query.Direction.ASCENDING)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(basePost.sentiScore, basePost.date)
            .limit(12)
            .get()
            .await()


        return@runBlocking querySnapshot.toPostsMutableList()
    }

    suspend fun loadWideRangeNextCollection(post: Post): List<Post> = runBlocking {

        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .orderBy("sentiScore", Query.Direction.ASCENDING)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(post.sentiScore, post.date)
            .limit(12)
            .get()
            .await()

        return@runBlocking querySnapshot.toPostsMutableList()
    }

    suspend fun loadPositiveTimeLineCollection(date: Date): MutableList<Post> {
        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(date)
            .limit(30)
            .get()
            .await()


        val results = ArrayList<Post>()

        querySnapshot.forEach {
            val post = it.toObject(Post::class.java)
            if (post.sentiScore >= -0.35f) results.add(post)
        }
        return results
    }

    override suspend fun loadFollowingCollection(date: Date): List<Post> = runBlocking {
        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(date)
            .limit(12)
            .get()
            .await()



        return@runBlocking querySnapshot.toPostsMutableList()
    }

    fun deleteItemFromDatabase(post: Post) {
        dataBase.collection(COLLECTION_PATH)
            .document(post.postId)
            .delete()
            .addOnSuccessListener { Log.d("FireStore", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("FireStore", "Error deleting document", e) }
    }

    fun increaseViews(postId: String) {
        val post = dataBase.collection(COLLECTION_PATH).document(postId)
        post.update("views", FieldValue.increment(1))

    }

    suspend fun loadDateRangedCollection(
        userId: String,
        oldDate: Date,
        currentDate: Date,
        limit: Long = 100
    ): MutableList<Post>  =  runBlocking{

        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .whereEqualTo("userId", userId)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAt(currentDate)
            .endAt(oldDate)
            .limit(limit)
            .get()
            .await()


        return@runBlocking querySnapshot.toPostsMutableList()

    }

    suspend fun loadScoreRangedCollectionPositive(
        limit: Long = 20,
        post: Post
    ): MutableList<Post> = runBlocking {

        val querySnapshot =
            dataBase.collection(COLLECTION_PATH)
                .orderBy("sentiScore", Query.Direction.ASCENDING)
                .orderBy("date", Query.Direction.DESCENDING)
                .startAfter(post.sentiScore, post.date)
                .limit(limit)
                .get()
                .await()


        return@runBlocking querySnapshot.toPostsMutableList()

    }

    suspend fun loadScoreRangedCollectionNegative(
        limit: Long = 20,
        post: Post
    ): MutableList<Post> = runBlocking {
        val querySnapshot =
            dataBase.collection(COLLECTION_PATH)
                .orderBy("sentiScore", Query.Direction.DESCENDING)
                .orderBy("date", Query.Direction.DESCENDING)
                .startAfter(post.sentiScore, post.date)
                .limit(limit)
                .get()
                .await()


        return@runBlocking querySnapshot.toPostsMutableList()
    }

    private fun QuerySnapshot.toPostsMutableList(): MutableList<Post> =
        this.map { it.toObject(Post::class.java) }.toMutableList()
}