package com.shojishunsuke.kibunnsns.clean_arc.domain


import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataConfigRepository

class MainActivityUsecase(private val dataConfigRepository: DataConfigRepository) {
    private val userRepository = FirebaseUserRepository()

    fun initialize() {
        val isInitialized = dataConfigRepository.isInitialized()
        if (!isInitialized) {
            dataConfigRepository.updateInitializationState()
        }
    }

    fun updateUser() {
        userRepository.updateUser()
    }
}