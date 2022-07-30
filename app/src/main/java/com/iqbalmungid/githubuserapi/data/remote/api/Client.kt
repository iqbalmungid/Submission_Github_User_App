package com.iqbalmungid.githubuserapi.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    private const val BASE_URL = "https://api.github.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance = retrofit.create(Api::class.java)
}