package com.example.rickmorty.data.local.remote.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonNames

data class EpisodeDto @OptIn(ExperimentalSerializationApi::class) constructor(
    val id: Int,
    val name: String,
    @JsonNames("air_date")
        val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)

data class EpisodeResponse(
    val info: InfoDto,
    val results: List<EpisodeDto>
)
