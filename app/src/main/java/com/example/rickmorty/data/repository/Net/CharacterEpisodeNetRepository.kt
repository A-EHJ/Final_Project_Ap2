package com.example.rickmorty.data.repository.Net

import com.example.rickmorty.data.Network.remote.Api.CharacterEpisodeApi
import com.example.rickmorty.data.Network.remote.dto.CharacterEpisodeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterEpisodeNetRepository(
    private val characterEpisodeApi: CharacterEpisodeApi
) {
    fun getAllCharacterEpisodes(): Flow<Resource<List<CharacterEpisodeDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = characterEpisodeApi.getAllCharacterEpisodes()
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}
