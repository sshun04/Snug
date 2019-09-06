package com.shojishunsuke.kibunnsns.clean_arc.data

import android.util.Log
import com.google.firebase.firestore.*
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.CloudPost
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

    private val collectionSnapShot = dataBase.collection(COLLECTION_PATH)


    override suspend fun savePost(post:Post) {

        dataBase.collection(COLLECTION_PATH)
            .document(post.postId)
            .set(post).await()

    }


    override suspend fun loadSpecificSortedNextCollection(basePost: Post): List<Post> =
        runBlocking {

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

    override suspend fun loadPositiveTimeLineCollection(date: Date): MutableList<Post> =
        runBlocking {
            val querySnapshot = collectionSnapShot
                .orderBy("date", Query.Direction.DESCENDING)
                .startAfter(date)
                .limit(30)
                .get()
                .await()

            Log.d("IsFromCache",querySnapshot.metadata.isFromCache.toString())

            val results = ArrayList<Post>()

            querySnapshot.forEach {
                val post = it.toObject(CloudPost::class.java)
                if (post.sentiScore >= -0.35f) results.add(post)
            }
            return@runBlocking results
        }

    override suspend fun loadFollowingCollection(date: Date): List<Post> = runBlocking {
        val querySnapshot = collectionSnapShot
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(date)
            .limit(12)
            .get()
            .await()



        return@runBlocking querySnapshot.toPostsMutableList()
    }

    fun deleteItemFromDatabase(cloudPost: CloudPost) {
        collectionSnapShot
            .document(cloudPost.postId)
            .delete()
            .addOnSuccessListener { Log.d("FireStore", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("FireStore", "Error deleting document", e) }
    }

    fun increaseViews(postId: String) {
        val post = collectionSnapShot
            .document(postId)
        post.update("views", FieldValue.increment(1))

    }

    override suspend fun loadDateRangedCollection(
        userId: String,
        oldDate: Date,
        currentDate: Date,
        limit: Long
    ): MutableList<Post> = runBlocking {

        val querySnapshot = collectionSnapShot
            .whereEqualTo("userId", userId)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAt(currentDate)
            .endAt(oldDate)
            .limit(limit)
            .get()
            .await()


        return@runBlocking querySnapshot.toPostsMutableList()

    }

    override suspend fun loadScoreRangedCollectionAscend(
        limit: Long,
        post: Post
    ): MutableList<Post> = runBlocking {

        val querySnapshot = collectionSnapShot
            .orderBy("sentiScore", Query.Direction.ASCENDING)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(post.sentiScore, post.date)
            .limit(limit)
            .get()
            .await()



        return@runBlocking querySnapshot.toPostsMutableList()
    }

    override suspend fun loadScoreRangedCollectionDescend(
        limit: Long,
        post: Post
    ): MutableList<Post> = runBlocking {
        val querySnapshot = collectionSnapShot
            .orderBy("sentiScore", Query.Direction.DESCENDING)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(post.sentiScore, post.date)
            .limit(limit)
            .get()
            .await()

        return@runBlocking querySnapshot.toPostsMutableList()
    }


    private fun QuerySnapshot.toPostsMutableList(): MutableList<Post> {
        Log.d("IsFromCache", this.metadata.isFromCache.toString())
        return this.map { it.toObject(CloudPost::class.java) }.toMutableList()
    }
}