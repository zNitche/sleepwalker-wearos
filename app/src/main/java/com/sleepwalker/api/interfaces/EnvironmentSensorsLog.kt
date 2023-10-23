package com.sleepwalker.api.interfaces

import com.google.gson.annotations.SerializedName

data class EnvironmentSensorsLog(
    @SerializedName("temperature")
    val temperature: Float,
    @SerializedName("humidity")
    val humidity: Float,
)
