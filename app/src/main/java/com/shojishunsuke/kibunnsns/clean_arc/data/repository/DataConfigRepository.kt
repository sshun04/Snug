package com.shojishunsuke.kibunnsns.clean_arc.data.repository

interface DataConfigRepository {
    fun isInitialized():Boolean
    fun getLatestCollection():List<String>
    fun updateInitializationState()
    fun updateCollection(currentValue:String)
}