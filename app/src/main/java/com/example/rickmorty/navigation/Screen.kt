package com.example.rickmorty.navigation

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    object CharacterList : Screen()

    @Serializable
    data class CharacterBody(val taskId: Int) : Screen()
}
