package com.example.rickmorty.data.repository

import com.example.rickmorty.data.local.dao.LocationDao
import com.example.rickmorty.data.local.entities.LocationEntity
import com.example.rickmorty.data.network.remote.Api.LocationApi
import com.example.rickmorty.data.network.remote.dto.LocationDto
import com.example.rickmorty.util.MinMaxIdResult
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val locationApi: LocationApi,
) {
    fun getLocations() = locationDao.getAllLocations()
    suspend fun insertLocations(locations: List<LocationEntity>) =
        locations.forEach { location -> locationDao.save(location) }

    suspend fun getLocationById(locationId: Int) = locationDao.getLocationById(locationId)

    suspend fun getLocationsLimited(startId: Int, text: String) =
        locationDao.getLocationsLimited(startId, text)

    suspend fun getMinMaxIdFiltered() =
        locationDao.getMinMaxIdFiltered()

    suspend fun getMinMaxIdLimitedFiltered(
        startId: Int,
    ): MinMaxIdResult {
        val minId = locationDao.getMinIdLimitedFiltered(
            startId = startId,
        )
        val maxId = locationDao.getMaxIdLimitedFiltered(
            startId = startId,
        )
        return MinMaxIdResult(minId ?: 0, maxId ?: 0)
    }

    suspend fun getAllLocations(): List<LocationDto> {
        return locationApi.getAllLocations()
    }
}
