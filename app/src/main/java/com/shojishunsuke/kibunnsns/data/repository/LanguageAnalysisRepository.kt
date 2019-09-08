package com.shojishunsuke.kibunnsns.data.repository

interface LanguageAnalysisRepository {
    suspend fun analyzeText(text: String): Triple<Float, Float, String>
}