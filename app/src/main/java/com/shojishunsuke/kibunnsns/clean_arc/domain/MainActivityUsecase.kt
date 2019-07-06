package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.RoomDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.DataConfigRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LanguageAnalysisRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityUsecase(private val dataConfigRepository: DataConfigRepository,private val languageAnalysisRepository: LanguageAnalysisRepository) {


    fun initialize(){
        val isInitialized= dataConfigRepository.isInitialized()
        if (!isInitialized){
            dataConfigRepository.updateInitializationState()
            GlobalScope.launch {
//                roomRepository.initializeUser()
            }
        }
    }
}