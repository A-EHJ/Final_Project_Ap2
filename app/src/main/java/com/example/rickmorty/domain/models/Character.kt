package com.example.rickmorty.domain.models

import com.example.rickmorty.data.local.entities.CharacterEntity

data class Character(
    val character: CharacterEntity = CharacterEntity(0, "", "", "", "", "", 0, 0, ""),
    val location: String = "",
    val origin: String = "",
)
