package com.example.rickmorty.data.repository

import com.example.rickmorty.data.local.dao.CharacterDao
import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.network.remote.Api.CharacterApi
import com.example.rickmorty.data.network.remote.dto.CharacterDto
import com.example.rickmorty.util.MinMaxIdResult
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterApi: CharacterApi,
) {
    suspend fun getCharacterById(id: Int) = characterDao.getCharacterById(id)

    suspend fun insertCharacters(characters: List<CharacterEntity>) {
        characters.forEach { character ->
            characterDao.save(character)
        }
    }

    suspend fun getCharactersLimited(startId: Int) = characterDao.getCharactersLimited(startId)

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
    ): List<CharacterEntity> {
        val alive = if (statusAlive) "Alive" else ""
        val dead = if (statusDead) "Dead" else ""
        val unknown = if (statusUnknown) "unknown" else ""
        val human = if (speciesHuman) "Human" else ""
        val cronenberg = if (speciesCronenberg) "Cronenberg" else ""
        val disease = if (speciesDisease) "Disease" else ""
        val poopybutthole = if (speciesPoopybutthole) "Poopybutthole" else ""
        val alien = if (speciesAlien) "Alien" else ""
        val unknownSpecies = if (speciesUnknown) "unknown" else ""
        val robot = if (speciesRobot) "Robot" else ""
        val animal = if (speciesAnimal) "Animal" else ""
        val mythologicalCreature = if (speciesMythologicalCreature) "Mythological Creature" else ""
        val humanoid = if (speciesHumanoid) "Humanoid" else ""
        val female = if (genderFemale) "Female" else ""
        val male = if (genderMale) "Male" else ""
        val genderless = if (genderGenderless) "Genderless" else ""
        val unknownGender = if (genderunknown) "unknown" else ""


        return characterDao.getCharactersLimitedFiltered(
            startId = startId,
            text = text,
            statusAlive = alive,
            statusDead = dead,
            statusUnknown = unknown,
            speciesHuman = human,
            speciesCronenberg = cronenberg,
            speciesDisease = disease,
            speciesPoopybutthole = poopybutthole,
            speciesAlien = alien,
            speciesUnknown = unknownSpecies,
            speciesRobot = robot,
            speciesAnimal = animal,
            speciesMythologicalCreature = mythologicalCreature,
            speciesHumanoid = humanoid,
            genderFemale = female,
            genderMale = male,
            genderGenderless = genderless,
            genderUnknown = unknownGender
        )
    }

    suspend fun getMinMaxIdLimitedFiltered(
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
    ): MinMaxIdResult {

        val alive = if (statusAlive) "Alive" else ""
        val dead = if (statusDead) "Dead" else ""
        val unknown = if (statusUnknown) "unknown" else ""
        val human = if (speciesHuman) "Human" else ""
        val cronenberg = if (speciesCronenberg) "Cronenberg" else ""
        val disease = if (speciesDisease) "Disease" else ""
        val poopybutthole = if (speciesPoopybutthole) "Poopybutthole" else ""
        val alien = if (speciesAlien) "Alien" else ""
        val unknownSpecies = if (speciesUnknown) "unknown" else ""
        val robot = if (speciesRobot) "Robot" else ""
        val animal = if (speciesAnimal) "Animal" else ""
        val mythologicalCreature = if (speciesMythologicalCreature) "Mythological Creature" else ""
        val humanoid = if (speciesHumanoid) "Humanoid" else ""
        val female = if (genderFemale) "Female" else ""
        val male = if (genderMale) "Male" else ""
        val genderless = if (genderGenderless) "Genderless" else ""
        val unknownGender = if (genderunknown) "unknown" else ""

        val minId = characterDao.getMinIdLimitedFiltered(
            startId = startId,
            text = text,
            statusAlive = alive,
            statusDead = dead,
            statusUnknown = unknown,
            speciesHuman = human,
            speciesCronenberg = cronenberg,
            speciesDisease = disease,
            speciesPoopybutthole = poopybutthole,
            speciesAlien = alien,
            speciesUnknown = unknownSpecies,
            speciesRobot = robot,
            speciesAnimal = animal,
            speciesMythologicalCreature = mythologicalCreature,
            speciesHumanoid = humanoid,
            genderFemale = female,
            genderMale = male,
            genderGenderless = genderless,
            genderUnknown = unknownGender
        )

        val maxId = characterDao.getMaxIdLimitedFiltered(
            startId = startId,
            text = text,
            statusAlive = alive,
            statusDead = dead,
            statusUnknown = unknown,
            speciesHuman = human,
            speciesCronenberg = cronenberg,
            speciesDisease = disease,
            speciesPoopybutthole = poopybutthole,
            speciesAlien = alien,
            speciesUnknown = unknownSpecies,
            speciesRobot = robot,
            speciesAnimal = animal,
            speciesMythologicalCreature = mythologicalCreature,
            speciesHumanoid = humanoid,
            genderFemale = female,
            genderMale = male,
            genderGenderless = genderless,
            genderUnknown = unknownGender
        )
        return MinMaxIdResult(minId ?: 0, maxId ?: 0)

    }

    suspend fun getMinMaxIdFiltered(
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
    ): MinMaxIdResult? {
        val alive = if (statusAlive) "Alive" else ""
        val dead = if (statusDead) "Dead" else ""
        val unknown = if (statusUnknown) "unknown" else ""
        val human = if (speciesHuman) "Human" else ""
        val cronenberg = if (speciesCronenberg) "Cronenberg" else ""
        val disease = if (speciesDisease) "Disease" else ""
        val poopybutthole = if (speciesPoopybutthole) "Poopybutthole" else ""
        val alien = if (speciesAlien) "Alien" else ""
        val unknownSpecies = if (speciesUnknown) "unknown" else ""
        val robot = if (speciesRobot) "Robot" else ""
        val animal = if (speciesAnimal) "Animal" else ""
        val mythologicalCreature = if (speciesMythologicalCreature) "Mythological Creature" else ""
        val humanoid = if (speciesHumanoid) "Humanoid" else ""
        val female = if (genderFemale) "Female" else ""
        val male = if (genderMale) "Male" else ""
        val genderless = if (genderGenderless) "Genderless" else ""
        val unknownGender = if (genderunknown) "unknown" else ""

        return characterDao.getMinMaxIdFiltered(
            text = text,
            statusAlive = alive,
            statusDead = dead,
            statusUnknown = unknown,
            speciesHuman = human,
            speciesCronenberg = cronenberg,
            speciesDisease = disease,
            speciesPoopybutthole = poopybutthole,
            speciesAlien = alien,
            speciesUnknown = unknownSpecies,
            speciesRobot = robot,
            speciesAnimal = animal,
            speciesMythologicalCreature = mythologicalCreature,
            speciesHumanoid = humanoid,
            genderFemale = female,
            genderMale = male,
            genderGenderless = genderless,
            genderUnknown = unknownGender
        )
    }

    suspend fun getMinCharacterId(): Int? = characterDao.getMinCharacterId()

    suspend fun getMaxCharacterId(): Int? = characterDao.getMaxCharacterId()

    suspend fun getAllCharacters(): List<CharacterDto> {
        return characterApi.getAllCharacters()
    }

}
