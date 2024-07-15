package com.example.rickmorty.data.local.remote.Api

import com.example.rickmorty.data.local.remote.dto.LocationDto
import com.example.rickmorty.data.local.remote.dto.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApi {

    @GET("/api/location")
    suspend fun getAllLocations(@Query("page") page: Int = 1): LocationResponse

    @GET("/api/location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): LocationDto

    @GET("/api/location")
    suspend fun getLocationsByIds(@Query("ids") ids: List<Int>): List<LocationDto>

    @GET("/api/location")
    suspend fun filterLocations(
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null,
        @Query("page") page: Int = 1
    ): LocationResponse
}
