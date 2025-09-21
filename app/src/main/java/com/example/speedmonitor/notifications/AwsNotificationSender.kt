package com.example.speedmonitor.notifications

import android.content.Context
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.speedmonitor.domain.model.SpeedEvent

class AwsNotificationSender(private val context: Context) : NotificationSender {
    private val channelId = "aws_alerts"

    override suspend fun sendToBackend(event: SpeedEvent) {
        // Call backend which should publish to AWS SNS.
        generateNotify(event)
    }

    override fun generateNotify(event: SpeedEvent) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(NotificationChannel(channelId, "AWS Alerts", NotificationManager.IMPORTANCE_HIGH))
        }
        val n = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("AWS SNS Alert - Overspeed")
            .setContentText("Renter ${event.customerId} speed ${event.currentSpeed} exceeded limit")
            .build()
        manager.notify(5002, n)
    }
}
