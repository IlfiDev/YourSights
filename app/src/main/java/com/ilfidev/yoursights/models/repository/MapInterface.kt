package com.ilfidev.yoursights.models.repository

import com.ilfidev.yoursights.models.data.MapPoint
import java.util.UUID

interface MapInterface {
    fun addMapPoint(newPoint: MapPoint)
    fun getMapPoint(pointId: UUID)

}