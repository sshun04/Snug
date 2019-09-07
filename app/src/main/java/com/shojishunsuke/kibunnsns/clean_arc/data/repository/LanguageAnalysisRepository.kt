package com.shojishunsuke.kibunnsns.clean_arc.data.repository

interface LanguageAnalysisRepository {
    suspend fun analyzeText(text: String): Triple<Float, Float, String>
}