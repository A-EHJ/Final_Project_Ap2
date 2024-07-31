package com.example.rickmorty.data.Network.remote.Api

import com.example.rickmorty.data.Network.remote.dto.LocationResidentDto
import retrofit2.http.GET

interface LocationResidentApi {
    @GET("api/location_resident")
    suspend fun getAllLocationResidents(): List<LocationResidentDto>
}
