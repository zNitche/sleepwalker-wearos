package com.sleepwalker.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface SleepwalkerApi {
    @GET("/api/auth-check/")
    suspend fun authCheck(@Header("X-API-KEY") apiKey: String): Response<Void>
}