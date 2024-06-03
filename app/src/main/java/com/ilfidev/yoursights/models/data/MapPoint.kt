package com.ilfidev.yoursights.models.data

import org.osmdroid.util.GeoPoint
import java.util.UUID

data class MapPoint(
    var id: UUID = UUID.randomUUID(),
    var name: String = "TestName",
    var city: String = "TestCity",
    var position: GeoPoint = GeoPoint(0.0, 0.0),
    var currentUsersData: String = "TestUserData",
    var allData: String = "Test All Data",
)
