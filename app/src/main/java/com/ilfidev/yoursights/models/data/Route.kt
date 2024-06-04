package com.ilfidev.yoursights.models.data

data class Route(
    val stops: List<MapPoint> = listOf(MapPoint()),
    val startTime: String? = null,
)