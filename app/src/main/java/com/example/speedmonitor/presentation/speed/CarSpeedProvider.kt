package com.example.speedmonitor.presentation.speed

import android.car.Car
import android.car.hardware.CarPropertyEvent
import android.car.hardware.CarPropertyManager
import android.content.Context
import android.util.Log

import kotlinx.coroutines.channels.awaitClose

import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.callbackFlow


class CarSpeedProvider(private val context: Context) {

    fun observeSpeed(): Flow<Int> = callbackFlow {
        var car: Car? = null
        var carPropertyManager: CarPropertyManager? = null

        try {
            car = Car.createCar(context)
            carPropertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

            val listener = CarPropertyManager.CarPropertyEventCallback { event: CarPropertyEvent ->
                try {
                    val value = event.carPropertyValue.value as? Float
                    if (value != null) trySend(value.toInt())
                } catch (e: Exception) {
                    Log.w("CarSpeedProvider", "Failed to read speed: ${'$'}{e.message}")
                }
            }

            // Register for vehicle speed property (property id constant may vary by AAOS version)
            val propertyId = CarPropertyManager.PERF_VEHICLE_SPEED
            carPropertyManager.registerCallback(listener, propertyId, CarPropertyManager.SENSOR_RATE_NORMAL)

            awaitClose {
                try {
                    carPropertyManager.unregisterCallback(listener)
                } catch (e: Exception) { }
                try { car?.disconnect() } catch (e: Exception) { }
            }

        } catch (e: Exception) {
            Log.e("CarSpeedProvider", "Car APIs unavailable: ${'$'}{e.message}")
            close(e)
        }
    }
}
