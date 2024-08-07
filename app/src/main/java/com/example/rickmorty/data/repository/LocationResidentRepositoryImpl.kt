package com.example.rickmorty.data.repository

import com.example.rickmorty.data.local.dao.LocationResidentDao
import com.example.rickmorty.data.local.entities.LocationResidentEntity
import com.example.rickmorty.data.network.remote.Api.LocationResidentApi
import com.example.rickmorty.data.network.remote.dto.LocationResidentDto
import javax.inject.Inject


class LocationResidentRepositoryImpl @Inject constructor(
    private val locationResidentDao: LocationResidentDao,
    private val locationResidentApi: LocationResidentApi,
) {
    suspend fun insertLocationResidents(locationResidents: List<LocationResidentEntity>) {
        locationResidents.forEach { locationResident ->
            locationResidentDao.save(locationResident)
        }
    }

    suspend fun getCharactersIdByLocation(locationId: Int): List<Int> =
        locationResidentDao.findLocation(locationId)

    suspend fun getAllLocationResidents(): List<LocationResidentDto> {
        return locationResidentApi.getAllLocationResidents()
    }
}
