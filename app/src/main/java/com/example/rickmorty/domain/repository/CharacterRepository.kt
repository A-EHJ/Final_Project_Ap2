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

    suspend fun getCharactersLimitedFiltered(
        startId: Int,
        text: String,
        statusAlive: Boolean,
        statusDead: Boolean,
        statusUnknown: Boolean,
        speciesHuman: Boolean,
        speciesCronenberg: Boolean,
        speciesDisease: Boolean,
        speciesPoopybutthole: Boolean,
        speciesAlien: Boolean,
        speciesUnknown: Boolean,
        speciesRobot: Boolean,
        speciesAnimal: Boolean,
        speciesMythologicalCreature: Boolean,
        speciesHumanoid: Boolean,
        genderFemale: Boolean,
        genderMale: Boolean,
        genderGenderless: Boolean,
        genderunknown: Boolean,
    ): List<Character> {
        val character = characterRepositoryImpl.getCharactersLimitedFiltered(
            startId,
            text,
            statusAlive,
            statusDead,
            statusUnknown,
            speciesHuman,
            speciesCronenberg,
            speciesDisease,
            speciesPoopybutthole,
            speciesAlien,
            speciesUnknown,
            speciesRobot,
            speciesAnimal,
            speciesMythologicalCreature,
            speciesHumanoid,
            genderFemale,
            genderMale,
            genderGenderless,
            genderunknown
        )
        val newCharacter = character.map {
            Character(
                character = it,
                location = getLocationById(it.location_Id),
                origin = getLocationById(it.origin_Id)
            )
        }

        return newCharacter
    }

    suspend fun getMinIdLimitedFiltered(
        startId: Int,
        text: String,
        statusAlive: Boolean,
        statusDead: Boolean,
        statusUnknown: Boolean,
        speciesHuman: Boolean,
        speciesCronenberg: Boolean,
        speciesDisease: Boolean,
        speciesPoopybutthole: Boolean,
        speciesAlien: Boolean,
        speciesUnknown: Boolean,
        speciesRobot: Boolean,
        speciesAnimal: Boolean,
        speciesMythologicalCreature: Boolean,
        speciesHumanoid: Boolean,
        genderFemale: Boolean,
        genderMale: Boolean,
        genderGenderless: Boolean,
        genderunknown: Boolean,
    ): Int? {
        val minId = characterRepositoryImpl.getMinIdLimitedFiltered(
            startId,
            text,
            statusAlive,
            statusDead,
            statusUnknown,
            speciesHuman,
            speciesCronenberg,
            speciesDisease,
            speciesPoopybutthole,
            speciesAlien,
            speciesUnknown,
            speciesRobot,
            speciesAnimal,
            speciesMythologicalCreature,
            speciesHumanoid,
            genderFemale,
            genderMale,
            genderGenderless,
            genderunknown
        )
        return minId
    }

    suspend fun getMaxIdLimitedFiltered(
        startId: Int,
        text: String,
        statusAlive: Boolean,
        statusDead: Boolean,
        statusUnknown: Boolean,
        speciesHuman: Boolean,
        speciesCronenberg: Boolean,
        speciesDisease: Boolean,
        speciesPoopybutthole: Boolean,
        speciesAlien: Boolean,
        speciesUnknown: Boolean,
        speciesRobot: Boolean,
        speciesAnimal: Boolean,
        speciesMythologicalCreature: Boolean,
        speciesHumanoid: Boolean,
        genderFemale: Boolean,
        genderMale: Boolean,
        genderGenderless: Boolean,
        genderunknown: Boolean,
    ): Int? {
        val maxId = characterRepositoryImpl.getMaIdLimitedFiltered(
            startId,
            text,
            statusAlive,
            statusDead,
            statusUnknown,
            speciesHuman,
            speciesCronenberg,
            speciesDisease,
            speciesPoopybutthole,
            speciesAlien,
            speciesUnknown,
            speciesRobot,
            speciesAnimal,
            speciesMythologicalCreature,
            speciesHumanoid,
            genderFemale,
            genderMale,
            genderGenderless,
            genderunknown
        )
        return maxId
    }

    suspend fun getMinIdFiltered(
        text: String,
        statusAlive: Boolean,
        statusDead: Boolean,
        statusUnknown: Boolean,
        speciesHuman: Boolean,
        speciesCronenberg: Boolean,
        speciesDisease: Boolean,
        speciesPoopybutthole: Boolean,
        speciesAlien: Boolean,
        speciesUnknown: Boolean,
        speciesRobot: Boolean,
        speciesAnimal: Boolean,
        speciesMythologicalCreature: Boolean,
        speciesHumanoid: Boolean,
        genderFemale: Boolean,
        genderMale: Boolean,
        genderGenderless: Boolean,
        genderunknown: Boolean,
    ): Int? {
        val minId = characterRepositoryImpl.getMinIdFiltered(
            text,
            statusAlive,
            statusDead,
            statusUnknown,
            speciesHuman,
            speciesCronenberg,
            speciesDisease,
            speciesPoopybutthole,
            speciesAlien,
            speciesUnknown,
            speciesRobot,
            speciesAnimal,
            speciesMythologicalCreature,
            speciesHumanoid,
            genderFemale,
            genderMale,
            genderGenderless,
            genderunknown
        )
        return minId
    }

    suspend fun getMaxIdFiltered(
        text: String,
        statusAlive: Boolean,
        statusDead: Boolean,
        statusUnknown: Boolean,
        speciesHuman: Boolean,
        speciesCronenberg: Boolean,
        speciesDisease: Boolean,
        speciesPoopybutthole: Boolean,
        speciesAlien: Boolean,
        speciesUnknown: Boolean,
        speciesRobot: Boolean,
        speciesAnimal: Boolean,
        speciesMythologicalCreature: Boolean,
        speciesHumanoid: Boolean,
        genderFemale: Boolean,
        genderMale: Boolean,
        genderGenderless: Boolean,
        genderunknown: Boolean,
    ): Int? {
        val maxId = characterRepositoryImpl.getMaIdFiltered(
            text,
            statusAlive,
            statusDead,
            statusUnknown,
            speciesHuman,
            speciesCronenberg,
            speciesDisease,
            speciesPoopybutthole,
            speciesAlien,
            speciesUnknown,
            speciesRobot,
            speciesAnimal,
            speciesMythologicalCreature,
            speciesHumanoid,
            genderFemale,
            genderMale,
            genderGenderless,
            genderunknown
        )
        return maxId
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
