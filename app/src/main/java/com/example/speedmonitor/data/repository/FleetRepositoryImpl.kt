package com.example.speedmonitor.data.repository

import android.content.Context
import com.example.speedmonitor.data.AwsSpeedReporter
import com.example.speedmonitor.data.remote.firebasedb.FirebaseSpeedReporter
import com.example.speedmonitor.domain.model.Customer
import com.example.speedmonitor.domain.model.NotificationChannelType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FleetRepositoryImpl(private val context: Context) : FleetRepository {
    private val firebaseReporter = FirebaseSpeedReporter()
    private val awsReporter = AwsSpeedReporter("https://your-backend.example.com/")

    override suspend fun getCustomer(customerId: String): Customer {
        // PoC: mock or fetch from backend. Example: alternate channel by id hash:
        val channel = if (customerId.hashCode() % 2 == 0) NotificationChannelType.FIREBASE else NotificationChannelType.AWS
        return Customer(id = customerId, speedLimit = 80, channel = channel)
    }

    override suspend fun reportSpeed(customerId: String, speed: Int) {
        withContext(Dispatchers.IO) {
            val customer = getCustomer(customerId)
            when (customer.channel) {
                NotificationChannelType.FIREBASE -> firebaseReporter.reportSpeed(customerId, speed)
                NotificationChannelType.AWS -> awsReporter.reportSpeed(customerId, speed)
            }
        }
    }
}
