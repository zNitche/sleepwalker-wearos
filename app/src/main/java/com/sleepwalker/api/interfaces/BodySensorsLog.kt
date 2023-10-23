package com.sleepwalker.api.interfaces

import com.google.gson.annotations.SerializedName

data class BodySensorsLog(
    @SerializedName("heart_beat")
    val heartBeat: Float,
    @SerializedName("acceleration_x")
    val accelerationX: Float,
    @SerializedName("acceleration_y")
    val accelerationY: Float,
    @SerializedName("acceleration_z")
    val accelerationZ: Float,
)
