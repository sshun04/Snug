package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.github.mikephil.charting.data.Entry
import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import kotlinx.coroutines.runBlocking
import java.util.*

class ChartActivityUsecase {
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private val useRepository = FirebaseUserRepository()
    private val userId = useRepository.getUserId()

    suspend fun requestDataOfDate(date:String):List<Entry> = runBlocking{
        val posts = fireStoreRepository.loadOwnCollectionsByDate(userId,date)
        val entryList= mutableListOf<Entry>()
        if (posts.isNotEmpty())
        posts.forEach {
            val time = formatDateToFloatTime(it.date)
            val entry = Entry(time,it.sentiScore)
            entryList.add(entry)
        }
        entryList
    }

    private  fun formatDateToFloatTime(date: Date):Float{
        val calendar = Calendar.getInstance()
        calendar.time = date
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)*100/6000
        return hour.toFloat() + minute.toFloat()
    }
}