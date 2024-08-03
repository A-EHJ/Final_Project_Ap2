package com.example.rickmorty.data.network.remote.Api

import com.example.rickmorty.data.network.remote.dto.CharacterDto
import retrofit2.http.GET

interface CharacterApi {
    @GET("/api/Characters/")
    suspend fun getAllCharacters(): List<CharacterDto>
}
