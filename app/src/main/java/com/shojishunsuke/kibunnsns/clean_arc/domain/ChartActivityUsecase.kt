package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking
import java.util.*

class ChartActivityUsecase {
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private val useRepository = FirebaseUserRepository()
    private val userId = useRepository.getUserId()


    suspend fun getDataOfDate(date: String): Pair<List<Entry>, List<PieEntry>> = runBlocking {
        val posts = fireStoreRepository.loadOwnCollectionsByDate(userId, date)
        val lineEntryList = mutableListOf<Entry>()

        if (posts.isNotEmpty()) {
            posts.forEach {
                val time = formatDateToFloatTime(it.date)
                val entry = Entry(time, formatSentiScore(it.sentiScore))
                lineEntryList.add(entry)
            }
        }
        val pieEntryList = getPieEntryListByScore(posts)

        Pair(lineEntryList, pieEntryList)
    }

    suspend fun getDataOfWeek(firstDayOfWeek: String): Pair<List<Entry>, List<PieEntry>> = runBlocking {
        val posts = fireStoreRepository.loadOwnCollectioonOfWeek(userId, firstDayOfWeek)
        val lineEntryList = mutableListOf<Entry>()
//        TODO
        if (posts.isNotEmpty()) {

        }

        val pieEntryList = getPieEntryListByScore(posts)

        Pair(listOf(Entry(0f, 0f)), pieEntryList)
    }

    suspend fun getDataOfMonth(yearMonth: String, daysOfMonth: Int): Pair<List<Entry>, List<PieEntry>> = runBlocking {
        val posts = fireStoreRepository.loadOwnCollectionOfMonth(userId, yearMonth, daysOfMonth)

//        TODO
        val pieEntryList = getPieEntryListByScore(posts)

        Pair(listOf(Entry(0f, 0f)), pieEntryList)
    }


    private fun getPieEntryListByScore(posts: List<Post>): List<PieEntry> = runBlocking {
        var positiveCount = 0f
        var neutralCount = 0f
        var negativeCount = 0f
        posts.forEach {
            when {
                it.sentiScore > 0.4f -> positiveCount++
                it.sentiScore <= 0.4f && it.sentiScore >= -0.4f -> neutralCount++
                it.sentiScore < -0.4f -> negativeCount++
            }
        }
        val values = listOf(positiveCount, neutralCount, negativeCount)
        val labels = listOf("Positive", "Neutral", "Negative")
        val pieEntries = mutableListOf<PieEntry>()
        for (i in values.indices) {
            val entry = PieEntry(values[i], labels[i])
            pieEntries.add(entry)
        }
        pieEntries
    }

    private fun formatDateToFloatTime(date: Date): Float {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE) * 100 / 6000
        return hour.toFloat() + minute.toFloat()
    }

    private fun formatSentiScore(score: Float): Float {

        return ((score * 10) + 10) / 5
    }

    private fun getAverageScoreListForEachDay(posts: List<Post>): List<Float> {
//        TODO
        return listOf(0f)
    }
}