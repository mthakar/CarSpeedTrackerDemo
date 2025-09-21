package com.example.speedmonitor.notifications

import com.example.speedmonitor.domain.model.SpeedEvent

interface NotificationSender {
    suspend fun sendToBackend(event: SpeedEvent)
    fun generateNotify(event: SpeedEvent)
}
