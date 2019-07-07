package com.shojishunsuke.kibunnsns.clean_arc.domain


import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataConfigRepository

class MainActivityUsecase(private val dataConfigRepository: DataConfigRepository) {

    fun initialize() {
        val isInitialized = dataConfigRepository.isInitialized()
        if (!isInitialized) {
            dataConfigRepository.updateInitializationState()
        }
    }

}