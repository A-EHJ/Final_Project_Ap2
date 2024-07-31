package com.example.rickmorty.data.repository.Net

import android.util.Log
import com.example.rickmorty.data.Network.remote.Api.LocationResidentApi
import com.example.rickmorty.data.Network.remote.dto.LocationResidentDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationResidentNetRepository @Inject constructor(
    private val locationResidentApi: LocationResidentApi
) {
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
