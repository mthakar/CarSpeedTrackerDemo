package com.example.speedmonitor.domain.repoter

interface SpeedReporter {
    suspend fun reportSpeed(customerId: String, speed: Int)
}