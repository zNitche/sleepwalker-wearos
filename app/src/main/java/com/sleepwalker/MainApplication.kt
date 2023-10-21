package com.sleepwalker

import android.app.Application
import android.hardware.SensorManager
import android.os.VibratorManager
import com.sleepwalker.services.ConfigService


const val APP_TAG = "sleepwalker"

class MainApplication : Application() {
    val configService by lazy { ConfigService(this) }
    val sensorManager by lazy { getSystemService(SENSOR_SERVICE) as SensorManager }
    val vibrationsManager by lazy { getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager }
}
