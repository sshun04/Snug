package com.shojishunsuke.kibunnsns.clean_arc.data.repository

interface DataConfigRepository {
    fun isInitialized():Boolean
    fun updateInitializationState()

}