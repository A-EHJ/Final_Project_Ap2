package com.example.rickmorty.data.repository

import android.util.Log
import com.example.rickmorty.data.local.dao.LocationResidentDao
import com.example.rickmorty.data.local.entities.LocationResidentEntity
import com.example.rickmorty.data.network.remote.Api.LocationResidentApi
import com.example.rickmorty.data.network.remote.dto.LocationResidentDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LocationResidentRepositoryImpl @Inject constructor(
    private val locationResidentDao: LocationResidentDao,
    private val locationResidentApi: LocationResidentApi
) {
    suspend fun insertLocationResident(locationResident: LocationResidentEntity) =
        locationResidentDao.save(locationResident)

    suspend fun insertLocationResidents(locationResidents: List<LocationResidentEntity>) {
        locationResidents.forEach { locationResident ->
            locationResidentDao.save(locationResident)
        }
    }


    fun getAllLocationResident(): Flow<Resource<List<LocationResidentDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = locationResidentApi.getAllLocationResidents()
            Log.d("CharacterNetRepository", "getAllCharacters: $users")
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}
