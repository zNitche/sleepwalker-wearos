package com.sleepwalker.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun getInstance(apiUrl: String): SleepwalkerApi? {
        try {
            return Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SleepwalkerApi::class.java)
        } catch (e: Exception) {
            return null
        }
    }
}
