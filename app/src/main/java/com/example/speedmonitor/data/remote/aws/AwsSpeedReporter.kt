package com.example.speedmonitor.data

import com.example.speedmonitor.data.remote.aws.AwsApiService
import com.example.speedmonitor.data.remote.aws.ReportSpeedRequest
import com.example.speedmonitor.domain.repoter.SpeedReporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AwsSpeedReporter(private val baseUrl: String = "https://your-backend.example.com/") :
    SpeedReporter {
    private val apiService: AwsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AwsApiService::class.java)
    }

    override suspend fun reportSpeed(customerId: String, speed: Int) {
        withContext(Dispatchers.IO) {
            try {
                val req = ReportSpeedRequest(customerId, speed, System.currentTimeMillis())
                apiService.reportSpeed(req)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
