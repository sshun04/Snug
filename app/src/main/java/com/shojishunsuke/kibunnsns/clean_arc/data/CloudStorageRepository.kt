package com.shojishunsuke.kibunnsns.clean_arc.data

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.StorageRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*

class CloudStorageRepository(private val uploadListener: ImageUploadListener) : StorageRepository {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    override suspend fun uploadImage(bitmap: Bitmap) {

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val path = "icons/" + UUID.randomUUID() + ".png"
        val iconsRef = storage.getReference(path)

        val uploadTask = iconsRef.putBytes(data)

        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    Log.d("uploading icon", "failure")
                }
            }
            return@Continuation iconsRef.downloadUrl
        }).addOnCompleteListener { task ->
            GlobalScope.launch {
                if (task.isSuccessful) {
                    val downloadUri = task.result ?: Uri.parse("")
                    uploadListener.onUploadTaskComplete(downloadUri)
                }
            }
        }
    }

    override fun getStorageRefByUri(uriString: String): StorageReference =
            storage.getReferenceFromUrl(uriString)

    interface ImageUploadListener {
        suspend fun onUploadTaskComplete(result: Uri)
    }
}