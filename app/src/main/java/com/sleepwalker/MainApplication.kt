package com.sleepwalker

import android.app.Application
import com.sleepwalker.services.HealthService


const val APP_TAG = "sleepwalker"
const val CONFIG_NAME = "config.json"

class MainApplication : Application() {
    val healthService by lazy { HealthService(this) }
}
