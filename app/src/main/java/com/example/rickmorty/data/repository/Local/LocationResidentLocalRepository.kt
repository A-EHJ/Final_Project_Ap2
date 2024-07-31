package com.example.rickmorty.data.repository.Local

import com.example.rickmorty.data.local.dao.LocationResidentDao
import com.example.rickmorty.data.local.entities.LocationResidentEntity
import javax.inject.Inject

class LocationResidentLocalRepository @Inject constructor(private val locationResidentDao: LocationResidentDao) {
    suspend fun insertLocationResident(locationResident: LocationResidentEntity) =
        locationResidentDao.save(locationResident)

    suspend fun insertLocationResidents(locationResidents: List<LocationResidentEntity>) {
        locationResidents.forEach { locationResident ->
            locationResidentDao.save(locationResident)
        }
    }
}
