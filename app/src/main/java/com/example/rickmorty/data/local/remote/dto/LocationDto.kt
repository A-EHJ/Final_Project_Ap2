package com.example.rickmorty.data.local.remote.dto


data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)

data class LocationResponse(
    val info: InfoDto,
    val results: List<LocationDto>
)
