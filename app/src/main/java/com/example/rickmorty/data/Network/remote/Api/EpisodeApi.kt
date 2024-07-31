package com.example.rickmorty.data.Network.remote.Api

import com.example.rickmorty.data.Network.remote.dto.EpisodeDto
import retrofit2.http.GET

interface EpisodeApi {
    @GET("/api/episodes")
    suspend fun getAllEpisodes(): List<EpisodeDto>
}
