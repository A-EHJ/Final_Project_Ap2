package com.example.rickmorty.data.repository.Local

import com.example.rickmorty.data.local.dao.LocationDao
import com.example.rickmorty.data.local.entities.LocationEntity
import javax.inject.Inject

class LocationLocalRepository @Inject constructor(private val locationDao: LocationDao) {
    fun getLocations() = locationDao.getAllLocations()
    suspend fun insertLocation(location: LocationEntity) = locationDao.save(location)
    suspend fun insertLocations(locations: List<LocationEntity>) =
        locations.forEach { location -> locationDao.save(location) }

    suspend fun deleteLocation(locationId: Int) = locationDao.delete(locationId)
}
