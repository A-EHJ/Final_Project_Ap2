package com.example.rickmorty.data.repository

import com.example.rickmorty.data.local.dao.CharacterEpisodeDao
import com.example.rickmorty.data.local.entities.CharacterEpisodeEntity
import com.example.rickmorty.data.network.remote.Api.CharacterEpisodeApi
import com.example.rickmorty.data.network.remote.dto.CharacterEpisodeDto
import javax.inject.Inject

class CharacterEpisodeRepositoryImpl @Inject constructor(
    private val characterEpisodeDao: CharacterEpisodeDao,
    private val characterEpisodeApi: CharacterEpisodeApi,

    ) {
    suspend fun insertCharacterEpisodes(characterEpisodes: List<CharacterEpisodeEntity>) {
        characterEpisodes.forEach { characterEpisode ->
            characterEpisodeDao.save(characterEpisode)
        }
    }

    suspend fun getAllCharacterEpisodes(): List<CharacterEpisodeDto> {
        return characterEpisodeApi.getAllCharacterEpisodes()
    }
}
