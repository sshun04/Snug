package com.shojishunsuke.kibunnsns.domain.use_case

import com.shojishunsuke.kibunnsns.data.repository.DataConfigRepository
import com.shojishunsuke.kibunnsns.data.repository.impl.FirebaseUserRepository

class MainActivityUseCase(private val dataConfigRepository: DataConfigRepository) {
    private val userRepository: FirebaseUserRepository = FirebaseUserRepository()

    fun initialize() {
        val isInitialized = dataConfigRepository.isInitialized()
        if (!isInitialized) {
            dataConfigRepository.updateInitializationState()
        }
    }

    fun getInitiazationState(): Boolean = dataConfigRepository.isInitialized()

    fun updateUser() {
        userRepository.updateUser()
    }
}