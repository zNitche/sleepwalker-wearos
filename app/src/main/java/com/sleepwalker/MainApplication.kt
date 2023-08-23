package com.sleepwalker

import android.app.Application
import android.hardware.SensorManager
import com.sleepwalker.services.ConfigService


const val APP_TAG = "sleepwalker"

class MainApplication : Application() {
    val configService by lazy { ConfigService(this) }
    val sensorManager by lazy { getSystemService(SENSOR_SERVICE) as SensorManager }
}
