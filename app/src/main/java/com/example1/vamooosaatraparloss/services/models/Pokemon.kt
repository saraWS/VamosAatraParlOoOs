package com.example1.vamooosaatraparloss.services.models

data class Pokemon(
    val name: String,
    val url: String
)

data class PokemonResponse(
    val pokemon_entries: List<PokemonEntry>
)

data class PokemonEntry(
    val pokemon_species: Pokemon
)