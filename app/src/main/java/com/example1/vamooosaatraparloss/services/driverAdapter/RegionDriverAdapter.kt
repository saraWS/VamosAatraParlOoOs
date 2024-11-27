package com.example1.vamooosaatraparloss.services.driverAdapter


import com.example1.vamooosaatraparloss.services.models.Region
import com.example1.vamooosaatraparloss.services.controllers.BaseService
import com.example1.vamooosaatraparloss.services.endPoints.RegionEndpoints

class RegionDriverAdapter {
    private val regionService = BaseService.retrofit.create(RegionEndpoints::class.java)

    // Método para obtener todas las regiones
    suspend fun fetchRegions(): List<Region> {
        val response = regionService.getRegions() // Asegúrate de que este método exista en `RegionEndpoints`
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        } else {
            throw Exception("Error al obtener las regiones: ${response.code()}")
        }
    }

    // Método para obtener una región específica por su ID
    suspend fun fetchRegionById(id: Int): Region {
        val response = regionService.getRegionById(id)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("No se encontró la región con ID $id")
        } else {
            throw Exception("Error al obtener la región con ID $id: ${response.code()}")
        }
    }
}
