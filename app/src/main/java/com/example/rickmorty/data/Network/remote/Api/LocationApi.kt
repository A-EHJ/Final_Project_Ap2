package com.example.rickmorty.data.Network.remote.Api

import com.example.rickmorty.data.Network.remote.dto.LocationDto
import retrofit2.http.GET

interface LocationApi {
    @GET("/api/locations")
    suspend fun getAllLocations(): List<LocationDto>
}
