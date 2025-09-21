package com.example.speedmonitor.domain.model

data class SpeedEvent(
    val customerId: String,
    val currentSpeed: Int,
    val timestamp: Long = System.currentTimeMillis()
)
