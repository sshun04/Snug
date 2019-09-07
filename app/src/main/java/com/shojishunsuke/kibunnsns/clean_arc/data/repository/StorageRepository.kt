package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import android.graphics.Bitmap
import com.google.firebase.storage.StorageReference

interface StorageRepository {
    suspend fun uploadImage(bitmap: Bitmap)
    fun getStorageRefByUri(uriString: String): StorageReference
}