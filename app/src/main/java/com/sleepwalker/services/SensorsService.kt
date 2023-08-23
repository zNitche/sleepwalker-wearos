package com.sleepwalker.services

import android.hardware.SensorManager
import android.util.Log
import com.sleepwalker.APP_TAG
import com.sleepwalker.services.sensors.AccelerationSensor

class SensorsService(private val sensorManager: SensorManager) {
    private val accelerationSensor: AccelerationSensor = AccelerationSensor(sensorManager)

    fun setup() {
        accelerationSensor.startListening()
        accelerationSensor.setOnSensorValuesChangedListener { values ->
            Log.d(APP_TAG, "Sensor ${accelerationSensor.getSensorTypeId()} - $values")
        }

        Log.d(APP_TAG, "registered sensor listener")
    }

    fun tearDown() {
        accelerationSensor.stopListening()
        Log.d(APP_TAG, "Unregistered sensor listener")
    }
}