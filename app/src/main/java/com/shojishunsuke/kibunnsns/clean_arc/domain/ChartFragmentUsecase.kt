package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.model.Post
import kotlinx.coroutines.runBlocking
import java.time.DayOfWeek
import java.util.*

class ChartFragmentUsecase {
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private val useRepository = FirebaseUserRepository()
    private val userId = useRepository.getUserId()


    suspend fun getDataOfDate(date: Calendar): Pair<List<Entry>, List<PieEntry>> = runBlocking {
        val dateStart = date.clone() as Calendar
        dateStart.apply {
            set(Calendar.HOUR_OF_DAY,0)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,0)
        }
        val dateEnd = date.clone() as Calendar
        dateEnd.apply {
            set(Calendar.HOUR_OF_DAY,23)
            set(Calendar.MINUTE,59)
            set(Calendar.SECOND,59)
        }

        val posts = fireStoreRepository.loadDateRangedCollection(userId,dateStart.time,dateEnd.time)
        val lineEntryList = mutableListOf<Entry>()

        if (posts.isNotEmpty()) {
            posts.forEach {
                val time = formatDateToFloatTime(it.date)
                val entry = Entry(time, formatSentiScore(it.sentiScore))
                lineEntryList.add(entry)
            }
        }
        val pieEntryList = getPieEntryListBasedOnScore(posts)

        Pair(lineEntryList, pieEntryList)
    }

    suspend fun getDataOfWeek(firstDayOfWeek: Calendar,lastDayOfWeek: Calendar): Pair<List<Entry>, List<PieEntry>> = runBlocking {
        val posts = fireStoreRepository.loadDateRangedCollection(userId,firstDayOfWeek.time,lastDayOfWeek.time)
        val sentiScoreList = getAverageScoreMap(posts).toList()

        val lineEntryList = mutableListOf<Entry>()

        for ( i in sentiScoreList.indices){
            val sentiScore = sentiScoreList[i].second
            val entry = Entry(i.toFloat(),formatSentiScore(sentiScore))
            lineEntryList.add(entry)
        }

        val pieEntryList = getPieEntryListBasedOnScore(posts)

        Pair(lineEntryList, pieEntryList)
    }

    suspend fun getDataOfMonth(date:Calendar): Pair<List<Entry>, List<PieEntry>> = runBlocking {
        val firstDayOfMonth = date.clone()as Calendar
        firstDayOfMonth.apply {
            set(Calendar.DAY_OF_MONTH,1)
            set(Calendar.HOUR_OF_DAY,0)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,0)
        }

        val lastDayOfMonth = date.clone() as Calendar

        lastDayOfMonth.apply {
            set(Calendar.DAY_OF_MONTH,getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY,23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND,59)
        }

        val posts = fireStoreRepository.loadDateRangedCollection(userId,firstDayOfMonth.time,lastDayOfMonth.time)
        val sentiScoreMap = getAverageScoreMap(posts)

        val lineEntryList = mutableListOf<Entry>()
        sentiScoreMap.keys.forEach { day ->
            val floatDay = day.toFloat()
            val sentiScore = sentiScoreMap[day] ?: 2f
            val entry = Entry(floatDay, formatSentiScore(sentiScore))
            lineEntryList.add(entry)
        }


        val pieEntryList = getPieEntryListBasedOnScore(posts)

        Pair(lineEntryList, pieEntryList)
    }


    private fun getPieEntryListBasedOnScore(posts: List<Post>): List<PieEntry> = runBlocking {
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
            if (values[i] > 0) {
                val entry = PieEntry(values[i], labels[i])
                pieEntries.add(entry)
            }
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

    private fun getAverageScoreMap(posts: List<Post>): Map<Int, Float> {

//     日付 : その日のスコアのリスト を 日付 : その日の平均スコア に変換
        val defMap = mutableMapOf<Int, MutableList<Float>>()
        posts.forEach {
            val calendar = Calendar.getInstance()
            calendar.time = it.date
            val date = calendar.get(Calendar.DAY_OF_MONTH)
            if (defMap.containsKey(date)) {
                defMap[date]?.add(it.sentiScore)
            } else {
                defMap.set(date, mutableListOf(it.sentiScore))
            }
        }

        val resultMap = mutableMapOf<Int, Float>()
        defMap.keys.forEach { day ->
            val scoreList = defMap[day]
            val average = scoreList?.average()?.toFloat() ?: 0f
            resultMap.set(day, average)
        }


        return resultMap
    }
}