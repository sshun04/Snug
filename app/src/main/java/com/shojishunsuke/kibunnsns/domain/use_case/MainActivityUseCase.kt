package com.shojishunsuke.kibunnsns.domain.use_case


import com.shojishunsuke.kibunnsns.data.repository.impl.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.data.repository.DataConfigRepository

class MainActivityUseCase(private val dataConfigRepository: DataConfigRepository) {
    private val userRepository : FirebaseUserRepository = FirebaseUserRepository()

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