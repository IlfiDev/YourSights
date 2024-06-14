package com.ilfidev.yoursights.Retrofit

import com.ilfidev.yoursights.models.data.MapPoint
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface ApiService {
    @GET("mapPoint/{uuid}")
    fun getMapPointByUuid(@Path("uuid") sightId: UUID) : Call<MapPoint>
}