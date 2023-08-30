package com.sleepwalker.services.sensors

import android.hardware.Sensor
import android.hardware.SensorManager


class AccelerationSensor(
    sensorManager: SensorManager
): DeviceSensor(
    sensorManager = sensorManager,
    sensorType = Sensor.TYPE_ACCELEROMETER,
)

class HeartBeatSensor(
    sensorManager: SensorManager
): DeviceSensor(
    sensorManager = sensorManager,
    sensorType = Sensor.TYPE_HEART_RATE,
)

class AmbientTemperatureSensor(
    sensorManager: SensorManager
): DeviceSensor(
    sensorManager = sensorManager,
    sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE,
)

class PressureSensor(
    sensorManager: SensorManager
): DeviceSensor(
    sensorManager = sensorManager,
    sensorType = Sensor.TYPE_PRESSURE,
)

class RelativeHumiditySensor(
    sensorManager: SensorManager
): DeviceSensor(
    sensorManager = sensorManager,
    sensorType = Sensor.TYPE_RELATIVE_HUMIDITY,
)
