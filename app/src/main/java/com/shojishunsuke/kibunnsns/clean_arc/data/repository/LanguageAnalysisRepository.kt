package com.shojishunsuke.kibunnsns.clean_arc.data.repository

import java.math.BigDecimal

interface LanguageAnalysisRepository {
   suspend fun getScore(text:String):Float
}