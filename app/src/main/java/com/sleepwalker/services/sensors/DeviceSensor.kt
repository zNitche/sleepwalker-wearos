package com.sleepwalker.services.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


abstract class DeviceSensor(
    private val sensorManager: SensorManager,
    private val sensorType: Int,
    private val refreshRate: Int = SensorManager.SENSOR_DELAY_NORMAL
): SensorEventListener {
    private var sensor: Sensor? = null
    private var onSensorValuesChanged: ((List<Float>) -> Unit)? = null

    fun getSensorTypeId(): Int {
        return sensorType
    }

    fun startListening() {
        if(sensor == null) {
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor?.let {
            sensorManager.registerListener(this, it, refreshRate)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    fun setOnSensorValuesChangedListener(listener: (List<Float>) -> Unit) {
        onSensorValuesChanged = listener
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == sensorType) {
            onSensorValuesChanged?.invoke(event.values.toList())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit
}
