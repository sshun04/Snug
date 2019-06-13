package com.shojishunsuke.kibunnsns.clean_arc.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataBaseRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FireStoreDataBaseRepository : DataBaseRepository {

    private val dataBase = FirebaseFirestore.getInstance()

    override fun savePost(post: Post) {
        GlobalScope.launch {
            dataBase.collection("posts")
                .document()
                .set(post)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("FireStoreDataBase", "Success")
                    } else {
                        Log.d("FireStoreDatabase", "${it.exception}")
                    }
                }
        }

    }

    override suspend fun getFilteredCollection(fieldName: String, params: Any): List<Post> = runBlocking {

        val results = ArrayList<Post>()


        val querySnapshot = dataBase.collection("posts")
            .get().await()

        for (result in querySnapshot) {
            val post = result.toObject(Post::class.java)
            results.add(post)
        }
//                        task ->
//                    if (task.isSuccessful) {
//                        task.result?.forEach {
//                            val post = it.toObject(Post::class.java)
//                            results.add(post)
//                        }
//                    } else {
//                        Log.d("FireStoreDataBase", "Error loading Collection")
//                    }


        results
    }


}