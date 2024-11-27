package com.example1.vamooosaatraparloss.services.controllers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseService {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}