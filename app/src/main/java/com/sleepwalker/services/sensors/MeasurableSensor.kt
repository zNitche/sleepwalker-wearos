package com.sleepwalker.services.sensors

abstract class MeasurableSensor(
    protected val sensorType: Int
) {
    protected var onSensorValuesChanged: ((List<Float>) -> Unit)? = null

    abstract fun startListening()
    abstract fun stopListening()

    fun getSensorTypeId(): Int {
        return sensorType
    }

    fun setOnSensorValuesChangedListener(listener: (List<Float>) -> Unit) {
        onSensorValuesChanged = listener
    }
}
