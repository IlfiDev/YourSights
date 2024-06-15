package com.ilfidev.yoursights.models.data

import com.ilfidev.yoursights.R

data class Route(
    val stops: List<MapPoint> = listOf(MapPoint()),
    val startTime: String? = null,
    val name: String? = null,
    val resource: Int = R.drawable.museum_big
)