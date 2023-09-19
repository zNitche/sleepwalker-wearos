package com.sleepwalker.api

import retrofit2.Response
import retrofit2.http.GET

interface SleepwalkerApi {
    @GET("/api/auth-check/")
    suspend fun authCheck(): Response<Void>
}