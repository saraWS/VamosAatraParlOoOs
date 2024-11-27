package com.example1.vamooosaatraparloss.services.endPoints

import com.example1.vamooosaatraparloss.services.models.PokemonDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonDetailsEndpoint {
    @GET("pokemon/{idOrName}")
    suspend fun getPokemonDetails(@Path("idOrName") idOrName: String): PokemonDetails
}