package com.example.speedmonitor.data.repository

import android.content.Context
import com.example.speedmonitor.data.remote.FleetApiService
import com.example.speedmonitor.domain.model.Customer
import com.example.speedmonitor.domain.model.SpeedEvent
import com.example.speedmonitor.domain.model.NotificationChannelType
import com.example.speedmonitor.notifications.FcmNotificationSender
import com.example.speedmonitor.notifications.AwsNotificationSender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FleetRepositoryImpl(private val context: Context) : FleetRepository {

    // Replace baseUrl with your backend endpoint
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://localhost.com/v1")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api by lazy { retrofit.create(FleetApiService::class.java) }

    override suspend fun getCustomer(customerId: String): Customer {
        // In a real app call backend; PoC returns mocked customer per id
        // For demo: alternate channel based on id hash
        val channel = if (customerId.hashCode() % 2 == 0) NotificationChannelType.FIREBASE else NotificationChannelType.AWS
        return Customer(id = customerId, speedLimit = 80, channel = channel)
    }

    override suspend fun reportSpeed(event: SpeedEvent) {
        // Post to backend for logging / notification
        withContext(Dispatchers.IO) {
            try {
                api.reportSpeed(event)
            } catch (e: Exception) {
                // If backend unavailable, fallback to local notification senders
                val customer = getCustomer(event.customerId)
                when (customer.channel) {
                    NotificationChannelType.FIREBASE -> FcmNotificationSender(context).simulateNotify(event)
                    NotificationChannelType.AWS -> AwsNotificationSender(context).simulateNotify(event)
                }
            }
        }
    }
}
