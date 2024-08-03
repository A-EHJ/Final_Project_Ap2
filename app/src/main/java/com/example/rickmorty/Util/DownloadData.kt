package com.example.rickmorty.Util

import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.local.entities.CharacterEpisodeEntity
import com.example.rickmorty.data.local.entities.EpisodeEntity
import com.example.rickmorty.data.local.entities.LocationEntity
import com.example.rickmorty.data.local.entities.LocationResidentEntity
import com.example.rickmorty.data.network.remote.Api.CharacterApi
import com.example.rickmorty.data.network.remote.Api.CharacterEpisodeApi
import com.example.rickmorty.data.network.remote.Api.EpisodeApi
import com.example.rickmorty.data.network.remote.Api.LocationApi
import com.example.rickmorty.data.network.remote.Api.LocationResidentApi
import com.example.rickmorty.data.repository.CharacterEpisodeRepositoryImpl
import com.example.rickmorty.data.repository.CharacterRepositoryImpl
import com.example.rickmorty.data.repository.EpisodeRepositoryImpl
import com.example.rickmorty.data.repository.LocationRepositoryImpl
import com.example.rickmorty.data.repository.LocationResidentRepositoryImpl
import javax.inject.Inject

class DownloadData @Inject constructor(


    private val characterRepositoryImpl: CharacterRepositoryImpl,
    private val locationRepositoryImpl: LocationRepositoryImpl,
    private val episodeRepositoryImpl: EpisodeRepositoryImpl,
    private val characterEpisodeRepositoryImpl: CharacterEpisodeRepositoryImpl,
    private val locationResidentRepositoryImpl: LocationResidentRepositoryImpl,

    private val characterApi: CharacterApi,
    private val locationApi: LocationApi,
    private val episodeApi: EpisodeApi,
    private val characterEpisodeApi: CharacterEpisodeApi,
    private val locationResidentApi: LocationResidentApi,
) {

    suspend fun downloadCharacterData() {
        try {
            val characters = characterDaoToEntities()
            characterRepositoryImpl.insertCharacters(characters)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun downloadLocationData() {
        try {
            val locations = locationDaoToEntities()
            locationRepositoryImpl.insertLocations(locations)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun downloadEpisodeData() {
        try {
            val episodes = episodeDaoToEntities()
            episodeRepositoryImpl.insertEpisodes(episodes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun downloadCharacterEpisodeData() {
        try {
            val characterEpisodes = characterEpisodeDaoToEntities()
            characterEpisodeRepositoryImpl.insertCharacterEpisodes(characterEpisodes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun downloadLocationResidentData() {
        try {
            val locationResidents = locationResidentDaoToEntities()
            locationResidentRepositoryImpl.insertLocationResidents(locationResidents)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun downloadAllData() {
        downloadCharacterData()
        downloadLocationData()
        downloadEpisodeData()
        downloadCharacterEpisodeData()
        downloadLocationResidentData()
    }

    private suspend fun locationResidentDaoToEntities(): List<LocationResidentEntity> {
        val locationResidents = locationResidentApi.getAllLocationResidents().map { dto ->
            LocationResidentEntity(
                id = 0, locationId = dto.locationid, characterId = dto.characterid
            )
        }
        return locationResidents
    }

    private suspend fun characterEpisodeDaoToEntities(): List<CharacterEpisodeEntity> {
        val characterEpisodes = characterEpisodeApi.getAllCharacterEpisodes().map { dto ->
            CharacterEpisodeEntity(
                id = 0, characterId = dto.character_Id, episodeId = dto.episode_Id
            )
        }
        return characterEpisodes
    }

    private suspend fun episodeDaoToEntities(): List<EpisodeEntity> {
        val episodes = episodeApi.getAllEpisodes().map { dto ->
            EpisodeEntity(
                id = dto.id, name = dto.name, episode = dto.episode, airDate = dto.air_Date
            )
        }
        return episodes
    }

    private suspend fun locationDaoToEntities(): List<LocationEntity> {
        val locations = locationApi.getAllLocations().map { dto ->
            LocationEntity(
                id = dto.id, name = dto.name, type = dto.type, dimension = dto.dimension
            )
        }
        return locations
    }

    private suspend fun characterDaoToEntities(): List<CharacterEntity> {
        val characters = characterApi.getAllCharacters().map { dto ->
            CharacterEntity(
                id = dto.id,
                name = dto.name,
                status = dto.status,
                species = dto.species,
                type = dto.type,
                gender = dto.gender,
                origin_Id = dto.origin_Id,
                location_Id = dto.location_Id,
                image = dto.image
            )
        }
        return characters
    }
}
