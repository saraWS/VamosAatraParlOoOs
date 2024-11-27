package com.example1.vamooosaatraparloss.services.endPoints

import com.example1.vamooosaatraparloss.services.models.Region
import com.example1.vamooosaatraparloss.services.models.RegionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RegionEndpoints {
    @GET("region/")
    suspend fun getRegions(): Response<RegionResponse> // Método para obtener todas las regiones

    @GET("region/{id}")
    suspend fun getRegionById(@Path("id") id: Int): Response<Region> // Método para obtener una región específica
}
