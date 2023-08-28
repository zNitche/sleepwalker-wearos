package com.sleepwalker.services

import android.hardware.SensorManager
import com.sleepwalker.services.sensors.AccelerationSensor
import com.sleepwalker.services.sensors.AmbientTemperatureSensor
import com.sleepwalker.services.sensors.DeviceSensor
import com.sleepwalker.services.sensors.HeartBeatSensor

class SensorsService(private val sensorManager: SensorManager) {
    private val sensors: List<DeviceSensor> = getSensors()

    private fun getSensors(): List<DeviceSensor> {
        return listOf(
            AccelerationSensor(sensorManager),
            HeartBeatSensor(sensorManager),
            AmbientTemperatureSensor(sensorManager)
        )
    }

    fun getSensorById(typeId: Int): DeviceSensor? {
        var sensor: DeviceSensor? = null;

        for (s in sensors) {
            if (s.getSensorTypeId() == typeId) {
                sensor = s
                break
            }
        }

        return sensor
    }

    fun initSensorById(typeId: Int, callback: (List<Float>) -> Unit): Boolean {
        var status = false
        val sensor = getSensorById(typeId)

        if (sensor != null) {
            sensor.startListening()
            sensor.setOnSensorValuesChangedListener({ values -> callback(values) })

            status = true
        }

        return status
    }

    fun tearDown() {
        for (sensor in sensors) {
            sensor.stopListening()
        }
    }
}