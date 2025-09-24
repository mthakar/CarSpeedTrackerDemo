package com.example.speedmonitor.data.remote.firebasedb

import com.example.speedmonitor.domain.repoter.SpeedReporter

class FirebaseSpeedReporter : SpeedReporter {
    private val db = Firebase.database

    override suspend fun reportSpeed(customerId: String, speed: Int) {
        val event = mapOf(
            "customerId" to customerId,
            "speed" to speed,
            "timestamp" to System.currentTimeMillis()
        )

        // push under /speed_reports
        db.getReference("speed_reports").push().setValue(event).await()
    }
}