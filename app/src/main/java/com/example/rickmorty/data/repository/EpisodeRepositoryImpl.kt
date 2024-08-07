package com.example.rickmorty.data.repository

import com.example.rickmorty.data.local.dao.EpisodeDao
import com.example.rickmorty.data.local.entities.EpisodeEntity
import com.example.rickmorty.data.network.remote.Api.EpisodeApi
import com.example.rickmorty.data.network.remote.dto.EpisodeDto
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val episodeDao: EpisodeDao,
    private val episodeApi: EpisodeApi,
) {
    fun getEpisodes() = episodeDao.getAllEpisodes()
    suspend fun getEpisode(id: Int) = episodeDao.getEpisodeById(id)
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>) {
        episodes.forEach { episode ->
            episodeDao.save(episode)
        }
    }

    suspend fun getAllEpisodes(): List<EpisodeDto> {
        return episodeApi.getAllEpisodes()
    }
}
