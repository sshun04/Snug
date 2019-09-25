package com.shojishunsuke.kibunnsns.presentation.secen.tutorial

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shojishunsuke.kibunnsns.data.repository.impl.SharedPrefRepository
import com.shojishunsuke.kibunnsns.domain.use_case.TutorialActivityUseCase

class TutorialViewModel(application: Application) : AndroidViewModel(application) {
    private val useCase: TutorialActivityUseCase

    init {
        val dataConfigRepository = SharedPrefRepository(application)
        useCase = TutorialActivityUseCase(dataConfigRepository)
    }

    fun onFinishTutorial() {
        useCase.updateInitializeState()
    }

    class TutorialActivityViewModelFactory(private val application: Application) :
        ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TutorialViewModel(application) as T
        }
    }
}