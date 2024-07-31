package com.example.rickmorty.data.repository.Net

import android.util.Log
import com.example.rickmorty.data.Network.remote.Api.LocationApi
import com.example.rickmorty.data.Network.remote.dto.LocationDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationNetRepository @Inject constructor(
    private val locationApi: LocationApi
) {
    fun getAllLocations(): Flow<Resource<List<LocationDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = locationApi.getAllLocations()
            Log.d("CharacterNetRepository", "getAllCharacters: $users")
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}
