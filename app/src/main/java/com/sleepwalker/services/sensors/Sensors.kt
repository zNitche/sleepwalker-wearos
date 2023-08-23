package com.sleepwalker.services.sensors

import android.hardware.Sensor
import android.hardware.SensorManager


class AccelerationSensor(
    sensorManager: SensorManager
): DeviceSensor(
    sensorManager = sensorManager,
    sensorType = Sensor.TYPE_ACCELEROMETER
)