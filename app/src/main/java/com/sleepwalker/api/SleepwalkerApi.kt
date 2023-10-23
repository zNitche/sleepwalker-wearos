package com.sleepwalker.api

import com.sleepwalker.api.interfaces.BodySensorsLog
import com.sleepwalker.api.interfaces.EnvironmentSensorsLog
import com.sleepwalker.api.interfaces.LogsSession
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SleepwalkerApi {
    @GET("/api/auth-check/")
    suspend fun authCheck(@Header("API-KEY") apiKey: String): Response<Void>

    @GET("/api/event-detected/")
    suspend fun eventDetected(@Header("API-KEY") apiKey: String): Response<Void>

    @POST("/api/sessions/init/")
    suspend fun initLogsSession(@Header("API-KEY") apiKey: String): Response<LogsSession>

    @POST("/api/sessions/{sessionUUID}/close/")
    suspend fun closeLogsSession(@Header("API-KEY") apiKey: String,
                                 @Path("sessionUUID") sessionUUID: String): Response<Void>

    @POST("/api/sessions/{sessionUUID}/body-sensors/add/")
    suspend fun addBodySensorsLog(@Header("API-KEY") apiKey: String,
                                  @Path("sessionUUID") sessionUUID: String,
                                  @Body body: BodySensorsLog): Response<Void>

    @POST("/api/sessions/{sessionUUID}/environment-sensors/add/")
    suspend fun addEnvironmentSensorsLog(@Header("API-KEY") apiKey: String,
                                         @Path("sessionUUID") sessionUUID: String,
                                         @Body body: EnvironmentSensorsLog): Response<Void>
}
