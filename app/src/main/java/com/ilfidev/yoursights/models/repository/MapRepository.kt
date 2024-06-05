package com.ilfidev.yoursights.models.repository

import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.data.Route
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.osmdroid.util.GeoPoint
import java.util.UUID

class MapOnlineRepository : MapInterface {

    private val _routesSharedFlow = MutableSharedFlow<Route>(replay = 1)
    override val routesSharedFlow: SharedFlow<Route> get() = _routesSharedFlow
    override suspend fun sendRoute(newRoute: Route) {
        TODO("Not yet implemented")
    }

    override suspend fun sendMapPoint(newPoint: MapPoint) {
        TODO("Not yet implemented")
    }

    override suspend fun addMapPoint(newPoint: MapPoint) {
        TODO("Not yet implemented")
    }

    override suspend fun getMapPoint(pointId: UUID){
//        return MapPoint()
    }

    override suspend fun getRoute(routeId: UUID){
        _routesSharedFlow.emit(
            Route(
                stops = listOf(
                    MapPoint(position = GeoPoint(55.5, 37.5)),
                    MapPoint(position = GeoPoint(55.6, 37.6)),
                    MapPoint(position = GeoPoint(55.5, 37.7))
                )
            )
        )
    }

    override suspend fun getRoutesWithSight(sightId: UUID) {
        TODO("Not yet implemented")
    }
}