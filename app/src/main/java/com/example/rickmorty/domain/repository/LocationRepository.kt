package com.example.rickmorty.domain.repository

import com.example.rickmorty.Util.MinMaxIdResult
import com.example.rickmorty.data.repository.CharacterRepositoryImpl
import com.example.rickmorty.data.repository.LocationRepositoryImpl
import com.example.rickmorty.data.repository.LocationResidentRepositoryImpl
import com.example.rickmorty.domain.models.CharacterIdUrl
import com.example.rickmorty.domain.models.LocationWithCharacterIdUrl
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val characterRepositoryImpl: CharacterRepositoryImpl,
    private val locationRepositoryImpl: LocationRepositoryImpl,
    private val locationResidentRepositoryImpl: LocationResidentRepositoryImpl,
) {
    suspend fun getLocations() = locationRepositoryImpl.getLocations()
    suspend fun getLocationById(locationId: Int) =
        locationRepositoryImpl.getLocationById(locationId)

    suspend fun getLocationResidents(locationId: Int): List<Int> =
        locationResidentRepositoryImpl.getCharactersIdByLocation(locationId)

    suspend fun getMinMaxIdLimitedFiltered(
        startId: Int,
    ): MinMaxIdResult {
        val minMaxId = locationRepositoryImpl.getMinMaxIdLimitedFiltered(startId)
        var maxId = minMaxId.maxId
        var minId = minMaxId.minId
        if (maxId == null) {
            maxId = 0
        }
        if (minId == null) {
            minId = 0
        }
        val minMaxIdResult = MinMaxIdResult(minId = minId, maxId = maxId)

        return minMaxIdResult
    }

    suspend fun getMinMaxIdFiltered(): MinMaxIdResult {
        val minMaxId = locationRepositoryImpl.getMinMaxIdFiltered()
        var maxId = minMaxId?.maxId
        var minId = minMaxId?.minId
        if (maxId == null) {
            maxId = 0
        }
        if (minId == null) {
            minId = 0
        }
        val minMaxIdResult = MinMaxIdResult(minId = minId, maxId = maxId)

        return minMaxIdResult
    }

    suspend fun getLocationsLimited(startId: Int): List<LocationWithCharacterIdUrl> {
        val locations = locationRepositoryImpl.getLocationsLimited(startId)
        val locationWithCharacterIdUrl = mutableListOf<LocationWithCharacterIdUrl>()
        for (location in locations) {
            val listCharacter = mutableListOf<CharacterIdUrl>()
            val charactersId = locationResidentRepositoryImpl.getCharactersIdByLocation(location.id)
            for (id in charactersId) {
                val character = characterRepositoryImpl.getCharacterById(id)
                listCharacter.add(CharacterIdUrl(character!!.id, character.image))
            }
            locationWithCharacterIdUrl.add(LocationWithCharacterIdUrl(location, listCharacter))
        }
        return locationWithCharacterIdUrl
    }
}
