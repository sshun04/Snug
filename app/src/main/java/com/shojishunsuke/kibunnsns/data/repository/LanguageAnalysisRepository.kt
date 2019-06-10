package com.shojishunsuke.kibunnsns.data.repository

interface LanguageAnalysisRepository {

   suspend fun getScore(text:String):Int


}