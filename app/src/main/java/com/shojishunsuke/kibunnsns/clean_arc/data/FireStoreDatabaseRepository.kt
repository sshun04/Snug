package com.shojishunsuke.kibunnsns.clean_arc.data

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FireStoreDatabaseRepository : DataBaseRepository {

    private val dataBase = FirebaseFirestore.getInstance()

    companion object {
        private const val COLLECTION_PATH = "betaTest"
    }

    override suspend fun savePost(post: Post) {

        dataBase.collection(COLLECTION_PATH)
            .document(post.postId)
            .set(post).await()

    }


    override suspend fun loadSortedNextCollection(basePost: Post): List<Post> {

        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .whereEqualTo("actID", basePost.actID)
            .orderBy("sentiScore", Query.Direction.ASCENDING)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(basePost.sentiScore, basePost.date)
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

    suspend fun loadWideRangeNextCollection(post: Post): List<Post> {

        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .orderBy("sentiScore", Query.Direction.ASCENDING)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(post.sentiScore, post.date)
            .limit(12)
            .get()
            .await()

        val results = ArrayList<Post>()

        querySnapshot.forEach {
            val result = it.toObject(Post::class.java)
            results.add(result)
        }

        return results
    }

    suspend fun loadPositiveCollection(previousPost: Post): List<Post> {
        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(previousPost.date)
            .limit(20)
            .get()
            .await()

        val results = ArrayList<Post>()

        querySnapshot.forEach {
            val post = it.toObject(Post::class.java)
            if (post.sentiScore >= -0.35f) results.add(post)
        }

        return results
    }

    override suspend fun loadFollowingCollection(previousPost: Post): List<Post> {
        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAfter(previousPost.date)
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

    suspend fun deleteItemFromDatabase(post: Post){
        dataBase.collection(COLLECTION_PATH)
            .document(post.postId)
            .delete()
            .addOnSuccessListener { Log.d("FireStore", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("FireStore", "Error deleting document", e) }
    }

    suspend fun increaseViews(postId:String){
        val post = dataBase.collection(COLLECTION_PATH).document(postId)
        post.update("views",FieldValue.increment(1))

    }

    suspend fun loadDateRangedCollection(userId: String, oldDate:Date, currentDate: Date,limit:Long = 100):MutableList<Post>{

        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .whereEqualTo("userId", userId)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAt(currentDate)
            .endAt(oldDate)
            .limit(limit)
            .get()
            .await()

        val results = mutableListOf<Post>()

        querySnapshot.forEach {
            val post = it.toObject(Post::class.java)
            results.add(post)
        }

        return results

    }
}