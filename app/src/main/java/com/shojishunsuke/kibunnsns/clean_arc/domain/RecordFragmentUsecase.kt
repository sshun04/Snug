package com.shojishunsuke.kibunnsns.clean_arc.domain

import com.shojishunsuke.kibunnsns.clean_arc.data.RoomDatabaseRepository
import com.shojishunsuke.kibunnsns.model.LocalPost

class RecordFragmentUsecase {
    private val roomRepositoy = RoomDatabaseRepository()

    suspend fun loadCollcetion():List<LocalPost>{
        return roomRepositoy.loadWholeCollection()
    }
}