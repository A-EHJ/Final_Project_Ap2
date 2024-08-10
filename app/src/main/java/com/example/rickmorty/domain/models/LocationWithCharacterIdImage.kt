package com.example.rickmorty.domain.models

import com.example.rickmorty.data.local.entities.LocationEntity

data class LocationWithCharacterIdImage(
    val location: LocationEntity = LocationEntity(0, "", "", ""),
    val characters: List<CharacterIdAndImage>,
)
