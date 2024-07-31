package com.example.rickmorty.data.repository.Net

import android.util.Log
import com.example.rickmorty.data.Network.remote.Api.CharacterApi
import com.example.rickmorty.data.Network.remote.dto.CharacterDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterNetRepository @Inject constructor(
    private val characterApi: CharacterApi
) {
    fun getAllCharacters(): Flow<Resource<List<CharacterDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = characterApi.getAllCharacters()
            Log.d("CharacterNetRepository", "getAllCharacters: $users")
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}
