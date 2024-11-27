package com.example1.vamooosaatraparloss.services.driverAdapter


import com.example1.vamooosaatraparloss.services.controllers.BaseService
import com.example1.vamooosaatraparloss.services.endPoints.PokemonDetailsEndpoint
import com.example1.vamooosaatraparloss.services.models.PokemonDetails

class PokemonDetailsDriverAdapter {
    private val pokemonDetailsService = BaseService.retrofit.create(PokemonDetailsEndpoint::class.java)

    suspend fun fetchPokemonDetails(idOrName: String): PokemonDetails {
        val response = pokemonDetailsService.getPokemonDetails(idOrName)
        if (response != null) {
            return response
        } else {
            throw Exception("Error al obtener detalles del Pokémon con ID o nombre: $idOrName")
        }
    }
}