package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.FireStoreDatabaseRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.FirebaseUserRepository
import com.shojishunsuke.kibunnsns.clean_arc.data.RoomPostDateRepository
import com.shojishunsuke.kibunnsns.model.Post
import java.util.*

class CalendarFragmentUsecase {
    private val fireStoreRepository = FireStoreDatabaseRepository()
    private val userRepository = FirebaseUserRepository()
    private val userId = userRepository.getUserId()
    private val postDateRepository = RoomPostDateRepository()

    suspend fun loadPostsByDate(date: Calendar): MutableList<Post> {

        val dateStart = date.clone() as Calendar
        dateStart.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val dateEnd = date.clone() as Calendar
        dateEnd.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }
        return fireStoreRepository.loadDateRangedCollection(userId, dateStart.time, dateEnd.time)
    }

    val postedDate = postDateRepository.loadDateList()
}