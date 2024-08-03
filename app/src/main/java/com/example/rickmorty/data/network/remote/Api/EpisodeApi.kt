package com.example.rickmorty.data.network.remote.Api

import com.example.rickmorty.data.network.remote.dto.EpisodeDto
import retrofit2.http.GET

interface EpisodeApi {
    @GET("/api/episodes")
    suspend fun getAllEpisodes(): List<EpisodeDto>
}
