package com.shojishunsuke.kibunnsns.clean_arc.data.repository

interface LanguageAnalysisRepository {

   suspend fun getScore(text:String):Int


}