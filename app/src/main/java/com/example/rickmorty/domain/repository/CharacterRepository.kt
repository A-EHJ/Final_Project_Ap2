package com.example.rickmorty.domain.repository

import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.repository.CharacterRepositoryImpl
import com.example.rickmorty.data.repository.LocationRepositoryImpl
import com.example.rickmorty.domain.models.Character
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val characterRepositoryImpl: CharacterRepositoryImpl,
    private val locationRepositoryImpl: LocationRepositoryImpl,
) {

    private suspend fun getLocationById(locationId: Int): String {
        return locationRepositoryImpl.getLocationById(locationId)?.name ?: ""
    }

    suspend fun getCharactersLimited(startId: Int): List<Character> {
        val character = characterRepositoryImpl.getCharactersLimited(startId)
        val newCharacter = character.map {
            Character(
                character = it,
                location = getLocationById(it.location_Id),
                origin = getLocationById(it.origin_Id)
            )
        }

        return newCharacter
    }

    suspend fun getCharacterById(id: Int): Character {
        val character = characterRepositoryImpl.getCharacterById(id)
        val location = getLocationById(character?.location_Id ?: 0)
        val origin = getLocationById(character?.origin_Id ?: 0)
        return Character(
            character = character ?: CharacterEntity(0, "", "", "", "", "", 0, 0, ""),
            location = location,
            origin = origin
        )
    }

    suspend fun getMaxCharacterId(): Int? {
        return characterRepositoryImpl.getMaxCharacterId()
    }

    suspend fun getMinCharacterId(): Int? {
        return characterRepositoryImpl.getMinCharacterId()
    }


}
