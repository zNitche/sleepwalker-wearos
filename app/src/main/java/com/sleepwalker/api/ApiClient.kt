package com.sleepwalker.api

import com.sleepwalker.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun getInstance(appConfig: Config): SleepwalkerApi? {
        try {
            return Retrofit.Builder()
                .baseUrl(appConfig.apiAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SleepwalkerApi::class.java)
        } catch (e: Exception) {
            return null
        }
    }
}
