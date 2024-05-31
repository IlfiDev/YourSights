package com.ilfidev.yoursights.models.data

import org.osmdroid.util.GeoPoint
import java.util.UUID

data class MapPoint(
    var id: UUID,
    var position: GeoPoint,
    var currentUsersData: String,
    var allData: String,
)
