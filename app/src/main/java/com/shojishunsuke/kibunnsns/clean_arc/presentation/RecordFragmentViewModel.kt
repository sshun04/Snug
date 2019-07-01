package com.shojishunsuke.kibunnsns.clean_arc.presentation

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.R
import com.shojishunsuke.kibunnsns.clean_arc.domain.RecordFragmentUsecase
import com.shojishunsuke.kibunnsns.model.LocalPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordFragmentViewModel : ViewModel() {

    private val _liveData = MutableLiveData<List<LocalPost>>()
    private val useCase = RecordFragmentUsecase()

    val liveData get() = _liveData


    val userName = MutableLiveData<String>()

    init {
        getUserName()
    }
//
//    fun loadWholeCollection() {
//        GlobalScope.launch {
//            val list = useCase.loadCollcetion()
//            launch(Dispatchers.IO) {
//                _liveData.postValue(list)
//            }
//        }
//    }



    fun saveUserName(name: String) {

        useCase.saveUserName(name)
        userName.postValue(name)
    }

    private fun getUserName() {
        userName.value = useCase.getUserName()
    }
}