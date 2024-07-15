package com.example.rickmorty.data.local.remote.Api

import com.example.rickmorty.data.local.remote.dto.EpisodeDto
import com.example.rickmorty.data.local.remote.dto.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApi {

    @GET("/api/episode")
    suspend fun getAllEpisodes(@Query("page") page: Int = 1): EpisodeResponse

    @GET("/api/episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): EpisodeDto

    @GET("/api/episode")
    suspend fun getEpisodesByIds(@Query("ids") ids: List<Int>): List<EpisodeDto>

    @GET("/api/episode")
    suspend fun filterEpisodes(
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null,
        @Query("page") page: Int = 1
    ): EpisodeResponse
}
