package com.shojishunsuke.kibunnsns.clean_arc.data

import android.content.Context
import android.util.Log
import com.shojishunsuke.kibunnsns.clean_arc.data.repository.LanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.utils.tokenize
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class EmotionAnalysisRepository(context: Context) : LanguageAnalysisRepository {

    private val dictionaryMap = HashMap<String, Int>()

    private val CHARSET_NAME = "UTF-8"

    init {
        try {
            val csvFile = context.assets.open("pn.csv.m3.120408.trim")
            val bufferReader = BufferedReader(InputStreamReader(csvFile, CHARSET_NAME))

            var strLine = bufferReader.readLine()

            while (strLine != null) {
                val split: List<String> = strLine.split(",")

                if (split.size > 1) {
                    dictionaryMap.set(split[0], split[1].toInt())
                }

                strLine = bufferReader.readLine()
            }

        } catch (e: Exception) {
            e.stackTrace
            Log.d("FileInput:pn.csv", "failure")
        }

        try {
            val wagoFile = context.assets.open("wago.121808.pn")
            val bufferedReader = BufferedReader(InputStreamReader(wagoFile, CHARSET_NAME))

            var strLine = bufferedReader.readLine()

            while (strLine != null) {
                val split: List<String> = strLine.split(",")

                if (split.size > 1) {
                    dictionaryMap.set(split[1], split[0].toInt())
                }

                strLine = bufferedReader.readLine()
            }
        } catch (e: Exception) {
            e.stackTrace
            Log.d("FileInput:wago", "failure")
        }

        val date = Date()
        val format = SimpleDateFormat("yyyyMMddHHmmss",Locale.JAPAN)
        format.format(date)


    }

    override suspend fun analyzeText(text: String):Triple<Float,Float,String> = runBlocking {
        val tokens = tokenize(text)

        var score = 0
        for (token in tokens) {

            val baseForm = token.baseForm

            if (dictionaryMap.containsKey(baseForm)) {
                score += dictionaryMap[baseForm]!!
            }
        }

        val convertedScore =   BigDecimal(score).divide(BigDecimal(10))

        Triple(convertedScore.toFloat(),0.0f,"")
    }


}