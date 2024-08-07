package com.example.rickmorty.data.network.remote.Api

import retrofit2.http.GET

interface ConfigurationApi {
    @GET("/api/configurations/version")
    suspend fun getAllVersion(): Int
}
