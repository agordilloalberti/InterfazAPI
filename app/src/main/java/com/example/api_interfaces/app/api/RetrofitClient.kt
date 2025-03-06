package com.example.api_interfaces.app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val URL_BASE = "https://adat-api-graf-1.onrender.com"

    val api: APIService = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIService::class.java)
}