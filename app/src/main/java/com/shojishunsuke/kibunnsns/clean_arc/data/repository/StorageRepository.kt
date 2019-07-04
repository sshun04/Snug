package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import android.graphics.Bitmap
import android.net.Uri

interface StorageRepository {
//    ダウンロードに必要なUriを返す
    suspend fun uploadImage(bitmap: Bitmap)
//    suspend fun downloadImage(url:Uri)
}