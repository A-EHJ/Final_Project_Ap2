package com.example.rickmorty.data.network.remote.Api

import com.example.rickmorty.data.network.remote.dto.CharacterEpisodeDto
import retrofit2.http.GET

interface CharacterEpisodeApi {
    @GET("/api/Character_Episode")
    suspend fun getAllCharacterEpisodes(): List<CharacterEpisodeDto>
}
