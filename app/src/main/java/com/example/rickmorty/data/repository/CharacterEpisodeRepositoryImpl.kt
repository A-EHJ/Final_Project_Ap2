package com.example.rickmorty.data.repository

import com.example.rickmorty.data.local.dao.CharacterEpisodeDao
import com.example.rickmorty.data.local.entities.CharacterEpisodeEntity
import com.example.rickmorty.data.network.remote.Api.CharacterEpisodeApi
import com.example.rickmorty.data.network.remote.dto.CharacterEpisodeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterEpisodeRepositoryImpl @Inject constructor(
    private val characterEpisodeDao: CharacterEpisodeDao,
    private val characterEpisodeApi: CharacterEpisodeApi,

    ) {
    suspend fun insertCharacterEpisode(characterEpisode: CharacterEpisodeEntity) =
        characterEpisodeDao.save(characterEpisode)

    suspend fun insertCharacterEpisodes(characterEpisodes: List<CharacterEpisodeEntity>) {
        characterEpisodes.forEach { characterEpisode ->
            characterEpisodeDao.save(characterEpisode)
        }
    }

    fun getAllCharacterEpisodes(): Flow<Resource<List<CharacterEpisodeDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = characterEpisodeApi.getAllCharacterEpisodes()
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }


}
