package com.example.rickmorty.domain.models

import com.example.rickmorty.data.local.entities.LocationEntity

data class LocationWithCharacterIIN(
    val location: LocationEntity = LocationEntity(0, "", "", ""),
    val characters: List<CharacterIIN>,
)
