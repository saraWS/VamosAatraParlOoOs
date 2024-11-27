package com.example1.vamooosaatraparloss.services.models

data class RegionResponse(
    val results: List<Region>
)

data class Region(
    val name: String,
    val url: String // Necesario para obtener el ID
) {
    val id: Int
        get() = url.split("/").filter { it.isNotEmpty() }.last().toInt()
}