package com.example.rickmorty.data.local.repository

import com.example.rickmorty.data.local.remote.Api.CharacterApi
import com.example.rickmorty.data.local.remote.Api.CharacterResponse
import com.example.rickmorty.data.local.remote.dto.CharacterDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterApi: CharacterApi
) {
    fun getAllCharacters(page: Int): Flow<Resource<CharacterResponse>> = flow {
        emit(Resource.Loading())
        try {
            val users = characterApi.getAllCharacters(page)
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }

    suspend fun getCharacterById(characterId: Int): CharacterDto? {
        return try {
            characterApi.getCharacterById(characterId)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getCharactersByIds(ids: List<Int>): List<CharacterDto> {
        return try {
            characterApi.getCharactersByIds(ids)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun filterCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Flow<Resource<CharacterResponse>> = flow {
        emit(Resource.Loading())

        try {
            val users = characterApi.filterCharacters(name, status
                , species, type, gender)
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
        }
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
