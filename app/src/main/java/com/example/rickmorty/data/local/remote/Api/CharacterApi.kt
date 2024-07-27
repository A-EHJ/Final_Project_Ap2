package com.example.rickmorty.data.local.remote.Api

import com.example.rickmorty.data.local.remote.dto.CharacterDto
import com.example.rickmorty.data.local.remote.dto.InfoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("/api/character/?")
    suspend fun getAllCharacters(@Query("page") page: Int = 1): CharacterResponse

    @GET("/api/character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDto?

    @GET("/api/character")
    suspend fun getCharactersByIds(@Query("ids") ids: List<Int>): List<CharacterDto>

    @GET("/api/character")
    suspend fun filterCharacters(
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null,
        @Query("page") page: Int = 1
    ): CharacterResponse
}

data class CharacterResponse(
    val info: InfoDto,
    val results: List<CharacterDto>
)
