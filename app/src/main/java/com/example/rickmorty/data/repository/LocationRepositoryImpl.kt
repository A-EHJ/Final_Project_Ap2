package com.example.rickmorty.data.repository

import android.util.Log
import com.example.rickmorty.data.local.dao.LocationDao
import com.example.rickmorty.data.local.entities.LocationEntity
import com.example.rickmorty.data.network.remote.Api.LocationApi
import com.example.rickmorty.data.network.remote.dto.LocationDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val locationApi: LocationApi
) {
    fun getLocations() = locationDao.getAllLocations()
    suspend fun insertLocation(location: LocationEntity) = locationDao.save(location)
    suspend fun insertLocations(locations: List<LocationEntity>) =
        locations.forEach { location -> locationDao.save(location) }

    suspend fun getLocationById(locationId: Int) = locationDao.getLocationById(locationId)

    suspend fun deleteLocation(locationId: Int) = locationDao.delete(locationId)

    fun getAllLocations(): Flow<Resource<List<LocationDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = locationApi.getAllLocations()
            Log.d("CharacterNetRepository", "getAllCharacters: $users")
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
