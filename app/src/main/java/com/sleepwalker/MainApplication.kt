package com.sleepwalker

import android.app.Application
import android.hardware.SensorManager
import com.sleepwalker.services.ConfigService
import com.sleepwalker.services.HealthService


const val APP_TAG = "sleepwalker"

class MainApplication : Application() {
    val healthService by lazy { HealthService(this) }
    val configService by lazy { ConfigService(this) }
    val sensorManager by lazy { getSystemService(SENSOR_SERVICE) as SensorManager }
}
