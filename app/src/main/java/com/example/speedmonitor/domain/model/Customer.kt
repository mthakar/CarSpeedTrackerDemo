package com.example.speedmonitor.domain.model

enum class NotificationChannelType { FIREBASE, AWS }

data class Customer(
    val id: String,
    val name: String = "Demo Customer",
    val speedLimit: Int = 80,
    val channel: NotificationChannelType = NotificationChannelType.FIREBASE
)
