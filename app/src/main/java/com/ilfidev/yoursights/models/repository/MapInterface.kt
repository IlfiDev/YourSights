package com.ilfidev.yoursights.models.repository

import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.data.Route
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.util.UUID

interface MapInterface {
    private val _routesSharedFlow: MutableSharedFlow<Route>
        get() = MutableSharedFlow<Route>(replay = 1)
    val routesSharedFlow: SharedFlow<Route> get() = _routesSharedFlow
    suspend fun sendRoute(newRoute: Route)
    suspend fun sendMapPoint(newPoint: MapPoint)
    suspend fun addMapPoint(newPoint: MapPoint)
    suspend fun getMapPoint(pointId: UUID)
    suspend fun getRoute(routeId: UUID)
    suspend fun getRoutesWithSight(sightId: UUID)
}