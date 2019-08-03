package com.shojishunsuke.kibunnsns.clean_arc.data

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
        private const val COLLECTION_PATH = "testPosts"
    }

    override suspend fun savePost(post: Post) {

        dataBase.collection(COLLECTION_PATH)
            .document()
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

    suspend fun loadOwnCollectionsByDate(userId: String, date: String): List<Post> {

        val dateStart = "$date 00:00:00"
        val dateEnd = "$date 23:59:59"

        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN)


        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .whereEqualTo("userId", userId)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAt(sdf.parse(dateEnd))
            .endAt(sdf.parse(dateStart))
            .get()
            .await()

        val results = ArrayList<Post>()

        querySnapshot.forEach {
            val post = it.toObject(Post::class.java)
            results.add(post)
        }

        return results
    }

    suspend fun loadOwnCollectioonOfWeek(userId: String, firstDayOfWeek: String): List<Post> {

        val weekStart = "$firstDayOfWeek 00:00:00"
        val weekEnd = "${firstDayOfWeek + 6} 23:59:59"

        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN)


        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .whereEqualTo("userId", userId)
            .orderBy("date", Query.Direction.ASCENDING)
            .startAt(sdf.parse(weekStart))
            .endAt(sdf.parse(weekEnd))
            .get()
            .await()

        val results = ArrayList<Post>()

        querySnapshot.forEach {
            val post = it.toObject(Post::class.java)
            results.add(post)
        }

        return results
    }
    suspend fun loadDateRangedCollection(userId: String,startDate:Date,endDate: Date):List<Post>{

        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .whereEqualTo("userId", userId)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAt(endDate)
            .endAt(startDate)
            .get()
            .await()

        val results = ArrayList<Post>()

        querySnapshot.forEach {
            val post = it.toObject(Post::class.java)
            results.add(post)
        }

        return results

    }

    suspend fun loadOwnCollectionOfMonth(userId: String,yearMonth:String,nuberOfDay:Int):List<Post>{

        val monthStart = "$yearMonth/01 00:00:00"
        val monthEnd = "$yearMonth/$nuberOfDay 23:59:59"

        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN)


        val querySnapshot = dataBase.collection(COLLECTION_PATH)
            .whereEqualTo("userId", userId)
            .orderBy("date", Query.Direction.DESCENDING)
            .startAt(sdf.parse(monthEnd))
            .endAt(sdf.parse(monthStart))
            .get()
            .await()

        val results = ArrayList<Post>()

        querySnapshot.forEach {
            val post = it.toObject(Post::class.java)
            results.add(post)
        }

        return results

    }


}