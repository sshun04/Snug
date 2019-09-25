package com.shojishunsuke.kibunnsns.domain.use_case

import com.shojishunsuke.kibunnsns.data.repository.DataConfigRepository

class TutorialActivityUseCase(private val dataConfigRepository: DataConfigRepository) {
    fun updateInitializeState(){
        dataConfigRepository.updateInitializationState()
    }
}