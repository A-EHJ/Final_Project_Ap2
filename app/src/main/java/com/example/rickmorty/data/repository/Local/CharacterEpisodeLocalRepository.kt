package com.example.rickmorty.data.repository.Local

import com.example.rickmorty.data.local.dao.CharacterEpisodeDao
import com.example.rickmorty.data.local.entities.CharacterEpisodeEntity
import javax.inject.Inject

class CharacterEpisodeLocalRepository @Inject constructor(
    private val characterEpisodeDao: CharacterEpisodeDao
) {
    suspend fun insertCharacterEpisode(characterEpisode: CharacterEpisodeEntity) =
        characterEpisodeDao.save(characterEpisode)

    suspend fun insertCharacterEpisodes(characterEpisodes: List<CharacterEpisodeEntity>) {
        characterEpisodes.forEach { characterEpisode ->
            characterEpisodeDao.save(characterEpisode)
        }
    }
}
