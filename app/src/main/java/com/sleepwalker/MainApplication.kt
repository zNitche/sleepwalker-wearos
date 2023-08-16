package com.sleepwalker

import android.app.Application
import com.sleepwalker.services.HealthService


const val APP_TAG = "sleepwalker"

class MainApplication : Application() {
    val healthService by lazy { HealthService(this) }
}
