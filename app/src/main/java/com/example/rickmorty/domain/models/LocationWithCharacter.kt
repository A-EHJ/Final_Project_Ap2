package com.example.rickmorty.domain.models

import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.local.entities.LocationEntity

data class LocationWithCharacter(
    val location: LocationEntity = LocationEntity(0, "", "", ""),
    val characters: List<CharacterEntity>,
)
