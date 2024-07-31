package com.example.rickmorty.data.Network.remote.Api

import com.example.rickmorty.data.Network.remote.dto.CharacterDto
import retrofit2.http.GET

interface CharacterApi {
    @GET("/api/Characters/")
    suspend fun getAllCharacters(): List<CharacterDto>
}
