package com.example.speedmonitor.data.repository

import com.example.speedmonitor.domain.model.Customer
import com.example.speedmonitor.domain.model.SpeedEvent

interface FleetRepository {
    suspend fun getCustomer(customerId: String): Customer
    suspend fun reportSpeed(customerId: String, speed: Int)
}
