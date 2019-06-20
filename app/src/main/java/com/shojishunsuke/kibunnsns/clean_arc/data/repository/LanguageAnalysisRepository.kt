package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import java.math.BigDecimal

interface LanguageAnalysisRepository {
   suspend fun analyzeText(text:String):Pair<Float,String>
}