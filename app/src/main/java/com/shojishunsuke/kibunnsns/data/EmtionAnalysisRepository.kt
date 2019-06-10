package com.shojishunsuke.kibunnsns.data

import android.content.Context
import android.util.Log
import com.shojishunsuke.kibunnsns.data.repository.LanguageAnalysisRepository
import com.shojishunsuke.kibunnsns.utils.tokenize
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader

class EmtionAnalysisRepository(context: Context) : LanguageAnalysisRepository {

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

    }

    override suspend fun getScore(text: String): Int = runBlocking {
        val tokens = tokenize(text)

        var score = 0
        for (token in tokens) {

            val baseForm = token.baseForm

            if (dictionaryMap.containsKey(baseForm)) {
                score += dictionaryMap[baseForm]!!
            }
        }

        score
    }


}