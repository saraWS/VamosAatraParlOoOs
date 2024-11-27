package com.example1.vamooosaatraparloss.services.endPoints

import com.example1.vamooosaatraparloss.services.models.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonEndpoints {
    @GET("pokedex/{region}/")
    suspend fun getPokemonByRegion(@Path("region") region: String): Response<PokemonResponse>

}