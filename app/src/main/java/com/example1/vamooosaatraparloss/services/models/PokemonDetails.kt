package com.example1.vamooosaatraparloss.services.models


data class PokemonDetails(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<Type>,
    val moves: List<Move>,
    val sprites: PokemonSprites // Nuevo campo para las imágenes
)

data class Type(
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)

data class Move(
    val move: MoveInfo
)

data class MoveInfo(
    val name: String
)

data class PokemonSprites(
    val front_default: String?, // Imagen normal del Pokémon
    val front_shiny: String?    // Imagen shiny del Pokémon
)
