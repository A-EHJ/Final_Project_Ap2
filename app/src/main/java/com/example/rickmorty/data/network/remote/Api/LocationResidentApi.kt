package com.example.rickmorty.data.network.remote.Api

import com.example.rickmorty.data.network.remote.dto.LocationResidentDto
import retrofit2.http.GET

interface LocationResidentApi {
    @GET("api/location_resident")
    suspend fun getAllLocationResidents(): List<LocationResidentDto>
}
