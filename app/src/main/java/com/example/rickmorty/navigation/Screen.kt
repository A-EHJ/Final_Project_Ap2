package com.example.rickmorty.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object CharacterList : Screen()

    @Serializable
    data class CharacterBody(val characterId: Int) : Screen()

    @Serializable
    object LocationList : Screen()

    @Serializable
    data class LocationBody(val locationId: Int, val charactersId: List<Int>) : Screen()
}
