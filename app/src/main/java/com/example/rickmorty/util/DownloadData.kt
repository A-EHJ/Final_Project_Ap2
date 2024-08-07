package com.example.rickmorty.util

import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.local.entities.CharacterEpisodeEntity
import com.example.rickmorty.data.local.entities.EpisodeEntity
import com.example.rickmorty.data.local.entities.LocationEntity
import com.example.rickmorty.data.local.entities.LocationResidentEntity
import com.example.rickmorty.data.repository.CharacterEpisodeRepositoryImpl
import com.example.rickmorty.data.repository.CharacterRepositoryImpl
import com.example.rickmorty.data.repository.ConfigurationRepositoryImpl
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
    private val configurationRepositoryImpl: ConfigurationRepositoryImpl,
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

    suspend fun downloadConfigurationData() {
        try {
            val version = configurationRepositoryImpl.getVersionFromApi()
            configurationRepositoryImpl.upsertVersion(version)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun downloadAllData() {
        if (checkVersion()) {
            return
        }
        downloadCharacterData()
        downloadLocationData()
        downloadEpisodeData()
        downloadCharacterEpisodeData()
        downloadLocationResidentData()
        downloadConfigurationData()
    }

    private suspend fun locationResidentDaoToEntities(): List<LocationResidentEntity> {
        val locationResidents =
            locationResidentRepositoryImpl.getAllLocationResidents().map { dto ->
                LocationResidentEntity(
                    id = 0, locationId = dto.locationid, characterId = dto.characterid
                )
            }
        return locationResidents
    }


    private suspend fun characterEpisodeDaoToEntities(): List<CharacterEpisodeEntity> {
        val characterEpisodes =
            characterEpisodeRepositoryImpl.getAllCharacterEpisodes().map { dto ->
                CharacterEpisodeEntity(
                    id = 0, characterId = dto.character_Id, episodeId = dto.episode_Id
                )
            }
        return characterEpisodes
    }

    private suspend fun episodeDaoToEntities(): List<EpisodeEntity> {
        val episodes = episodeRepositoryImpl.getAllEpisodes().map { dto ->
            EpisodeEntity(
                id = dto.id, name = dto.name, episode = dto.episode, airDate = dto.air_Date
            )
        }
        return episodes
    }

    private suspend fun locationDaoToEntities(): List<LocationEntity> {
        val locations = locationRepositoryImpl.getAllLocations().map { dto ->
            LocationEntity(
                id = dto.id, name = dto.name, type = dto.type, dimension = dto.dimension
            )
        }
        return locations
    }

    private suspend fun characterDaoToEntities(): List<CharacterEntity> {
        val characters = characterRepositoryImpl.getAllCharacters().map { dto ->
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

    suspend fun checkVersion(): Boolean {
        var versionLocal: Int? = null
        var versionApi: Int? = null
        try {
            versionLocal = configurationRepositoryImpl.getVersionFromDb() ?: 0
            versionApi = configurationRepositoryImpl.getVersionFromApi()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return versionApi == versionLocal
    }
}
