package com.sleepwalker.api.interfaces

import com.google.gson.annotations.SerializedName

data class LogsSession(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end-date")
    val endDate: String,
)