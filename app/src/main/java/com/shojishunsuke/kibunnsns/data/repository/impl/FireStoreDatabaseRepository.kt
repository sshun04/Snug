package com.shojishunsuke.kibunnsns.data.repository.impl

import com.google.firebase.firestore.*
import com.shojishunsuke.kibunnsns.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.domain.model.Post
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class FireStoreDatabaseRepository : DataBaseRepository {
    private val dataBase: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        dataBase.firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }

    companion object {
        private const val COLLECTION_PATH = "betaTest"
    }

    private val collectionSnapShot = dataBase.collection(COLLECTION_PATH)

    override suspend fun savePost(post: Post) {
        dataBase.collection(COLLECTION_PATH)
            .document(post.postId)
            .set(post).await()
    }

    override suspend fun loadSpecificSortedNextCollection(basePost: Post): MutableList<Post> =
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

            val results = ArrayList<Post>()

            querySnapshot.forEach {
                val post = it.toObject(Post::class.java)
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

    override fun deleteItemFromDatabase(post: Post) {
        collectionSnapShot
            .document(post.postId)
            .delete()
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    fun increaseViews(postId: String) {
        val post = collectionSnapShot
            .document(postId)
        post.update("views", FieldValue.increment(1))
    }

    private fun QuerySnapshot.toPostsMutableList(): MutableList<Post> {
        return this.map { it.toObject(Post::class.java) }.toMutableList()
    }
}