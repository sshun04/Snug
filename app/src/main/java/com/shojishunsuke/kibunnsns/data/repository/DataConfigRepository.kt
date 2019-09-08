package com.shojishunsuke.kibunnsns.data.repository

interface DataConfigRepository {
    fun isInitialized(): Boolean
    fun updateInitializationState()
}