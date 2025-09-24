package com.example.speedmonitor.data.remote.aws

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class ReportSpeedRequest(val customerId: String, val speed: Int, val timestamp: Long)

interface AwsApiService {
    @POST("reportSpeed") // endpoint relative to baseUrl
    suspend fun reportSpeed(@Body request: ReportSpeedRequest): Response<Unit>
}