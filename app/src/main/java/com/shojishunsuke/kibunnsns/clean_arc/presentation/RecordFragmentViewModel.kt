package com.shojishunsuke.kibunnsns.clean_arc.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shojishunsuke.kibunnsns.clean_arc.domain.RecordFragmentUsecase
import com.shojishunsuke.kibunnsns.model.LocalPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordFragmentViewModel : ViewModel() {

    private val _liveData = MutableLiveData<List<LocalPost>>()
    private val useCase = RecordFragmentUsecase()

    val liveData get() = _liveData

    init {

    }

    fun loadWholeCollection() {
        GlobalScope.launch {

            val list = useCase.loadCollcetion()

            launch(Dispatchers.IO) {

                _liveData.postValue(list)
            }


        }

    }
}