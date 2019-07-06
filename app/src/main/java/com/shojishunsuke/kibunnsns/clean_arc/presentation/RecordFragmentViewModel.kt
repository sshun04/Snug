package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.StorageReference
import com.shojishunsuke.kibunnsns.clean_arc.data.CloudStorageRepository
import com.shojishunsuke.kibunnsns.clean_arc.domain.RecordFragmentUsecase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordFragmentViewModel : ViewModel(), CloudStorageRepository.ImageUploadListener {


    private val useCase = RecordFragmentUsecase(this)


    var currentBitmap: Bitmap? = null

    val userName = MutableLiveData<String>()

    init {
        getUserName()
    }

    fun saveUserIcon(bitmap: Bitmap) {
        GlobalScope.launch {
            useCase.saveIconToCloud(bitmap)
        }
        currentBitmap = bitmap
    }

    fun saveUserName(name: String) {

        useCase.saveUserName(name)
        userName.postValue(name)
    }

    private fun getUserName() {
        userName.value = useCase.getUserName()
    }

    fun getIconRef(): StorageReference {
        return useCase.getIconStorageRef()
    }

    override suspend fun onUploadTaskComplete(result: Uri) {
        GlobalScope.launch {
            useCase.saveLocalPhotoUri(result)
        }
//
    }


}