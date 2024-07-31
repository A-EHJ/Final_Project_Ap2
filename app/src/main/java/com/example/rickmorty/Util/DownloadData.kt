package com.example.rickmorty.Util

import android.annotation.SuppressLint
import com.example.rickmorty.data.Network.remote.Api.CharacterApi
import com.example.rickmorty.data.Network.remote.Api.CharacterEpisodeApi
import com.example.rickmorty.data.Network.remote.Api.EpisodeApi
import com.example.rickmorty.data.Network.remote.Api.LocationApi
import com.example.rickmorty.data.Network.remote.Api.LocationResidentApi
import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.local.entities.CharacterEpisodeEntity
import com.example.rickmorty.data.local.entities.EpisodeEntity
import com.example.rickmorty.data.local.entities.LocationEntity
import com.example.rickmorty.data.local.entities.LocationResidentEntity
import com.example.rickmorty.data.repository.Local.CharacterEpisodeLocalRepository
import com.example.rickmorty.data.repository.Local.CharacterLocalRepository
import com.example.rickmorty.data.repository.Local.EpisodeLocalRepository
import com.example.rickmorty.data.repository.Local.LocationLocalRepository
import com.example.rickmorty.data.repository.Local.LocationResidentLocalRepository
import javax.inject.Inject

class DownloadData @Inject constructor(


    private val characterLocalRepository: CharacterLocalRepository,
    private val locationLocalRepository: LocationLocalRepository,
    private val episodeLocalRepository: EpisodeLocalRepository,
    private val characterEpisodeLocalRepository: CharacterEpisodeLocalRepository,
    private val locationResidentLocalRepository: LocationResidentLocalRepository,

    private val characterApi: CharacterApi,
    private val locationApi: LocationApi,
    private val episodeApi: EpisodeApi,
    private val characterEpisodeApi: CharacterEpisodeApi,
    private val locationResidentApi: LocationResidentApi
) {
    @SuppressLint("NotConstructor")
    suspend fun downloadData() {


        val characters = characterDaoToEntities()
        val locations = locationDaoToEntities()
        val episodes = episodeDaoToEntities()
        val characterEpisodes = characterEpisodeDaoToEntities()
        val locationResidents = locationResidentDaoToEntities()


        characterLocalRepository.insertCharacters(characters)
        locationLocalRepository.insertLocations(locations)
        episodeLocalRepository.insertEpisodes(episodes)
        characterEpisodeLocalRepository.insertCharacterEpisodes(characterEpisodes)
        locationResidentLocalRepository.insertLocationResidents(locationResidents)
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
