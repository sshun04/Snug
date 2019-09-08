package com.shojishunsuke.kibunnsns.presentation.secen.main.record

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.StorageReference
import com.shojishunsuke.kibunnsns.data.repository.impl.CloudStorageRepository
import com.shojishunsuke.kibunnsns.domain.use_case.RecordFragmentUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordFragmentViewModel : ViewModel(), CloudStorageRepository.ImageUploadListener {
    private val useCase = RecordFragmentUseCase(this)
    var currentBitmap: Bitmap? = null

    val userName: LiveData<String> get() = _userName
    private val _userName: MutableLiveData<String> = MutableLiveData()

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
        _userName.postValue(name)
    }

    private fun getUserName() {
        _userName.value = useCase.getUserName()
    }

    fun getIconRef(): StorageReference {
        return useCase.getIconStorageRef()
    }

    override suspend fun onUploadTaskComplete(result: Uri) {
        GlobalScope.launch {
            useCase.saveLocalPhotoUri(result)
        }
    }
}