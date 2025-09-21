package com.example.speedmonitor.data.remote

import com.example.speedmonitor.domain.model.SpeedEvent
import retrofit2.http.Body
import retrofit2.http.POST

interface FleetApiService {
    @POST("/reportSpeed")
    suspend fun reportSpeed(@Body event: SpeedEvent)
}
