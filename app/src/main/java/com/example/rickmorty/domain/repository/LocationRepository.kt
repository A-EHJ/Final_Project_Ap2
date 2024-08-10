package com.example.rickmorty.domain.repository

import com.example.rickmorty.data.repository.CharacterRepositoryImpl
import com.example.rickmorty.data.repository.LocationRepositoryImpl
import com.example.rickmorty.data.repository.LocationResidentRepositoryImpl
import com.example.rickmorty.domain.models.CharacterIIN
import com.example.rickmorty.domain.models.CharacterIdAndImage
import com.example.rickmorty.domain.models.LocationWithCharacterIIN
import com.example.rickmorty.domain.models.LocationWithCharacterIdImage
import com.example.rickmorty.util.MinMaxIdResult
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val characterRepositoryImpl: CharacterRepositoryImpl,
    private val locationRepositoryImpl: LocationRepositoryImpl,
    private val locationResidentRepositoryImpl: LocationResidentRepositoryImpl,
) {
    suspend fun getLocationById(
        locationId: Int,
        charactersId: List<Int>,
    ): LocationWithCharacterIIN {

        val listCharacter = mutableListOf<CharacterIIN>()
        for (id in charactersId) {
            val character = characterRepositoryImpl.getCharacterById(id)
            listCharacter.add(CharacterIIN(character!!.id, character.image, character.name))
        }

        return LocationWithCharacterIIN(
            locationRepositoryImpl.getLocationById(locationId)!!,
            listCharacter
        )

    }

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

    suspend fun getLocationsLimitedFiltered(
        startId: Int,
        text: String,
    ): List<LocationWithCharacterIdImage> {
        val locations = locationRepositoryImpl.getLocationsLimited(startId,text)
        val locationWithCharacterIdImage = mutableListOf<LocationWithCharacterIdImage>()
        for (location in locations) {
            val listCharacter = mutableListOf<CharacterIdAndImage>()
            val charactersId = locationResidentRepositoryImpl.getCharactersIdByLocation(location.id)
            for (id in charactersId) {
                val character = characterRepositoryImpl.getCharacterById(id)
                listCharacter.add(CharacterIdAndImage(character!!.id, character.image))
            }
            locationWithCharacterIdImage.add(LocationWithCharacterIdImage(location, listCharacter))
        }
        return locationWithCharacterIdImage
    }
}
