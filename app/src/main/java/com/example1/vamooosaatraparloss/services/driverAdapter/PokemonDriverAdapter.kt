package com.example1.vamooosaatraparloss.services.driverAdapter

import com.example1.vamooosaatraparloss.services.controllers.BaseService
import com.example1.vamooosaatraparloss.services.endPoints.PokemonEndpoints
import com.example1.vamooosaatraparloss.services.models.Pokemon

class PokemonDriverAdapter {
    private val pokemonService = BaseService.retrofit.create(PokemonEndpoints::class.java)

    suspend fun fetchPokemonByRegion(region: String): List<Pokemon> {
        val response = pokemonService.getPokemonByRegion(region)
        if (response.isSuccessful) {
            return response.body()?.pokemon_entries?.map { it.pokemon_species } ?: emptyList()
        } else {
            throw Exception("Error al obtener Pokémon: ${response.code()} - ${response.message()}")
        }
    }

}