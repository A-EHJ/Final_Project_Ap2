package com.example.rickmorty.data.repository

import android.util.Log
import com.example.rickmorty.data.local.dao.CharacterDao
import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.network.remote.Api.CharacterApi
import com.example.rickmorty.data.network.remote.dto.CharacterDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterApi: CharacterApi,
) {
    fun getCharacters() = characterDao.getAllCharacters()

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


        return characterDao.getMinIdLimitedFiltered(
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


    suspend fun getMaIdLimitedFiltered(
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


        return characterDao.getMaxIdLimitedFiltered(
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


        return characterDao.getMinIdFiltered(
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


    suspend fun getMaIdFiltered(
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


        return characterDao.getMaxIdFiltered(
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


    suspend fun insertCharacter(character: CharacterEntity) = characterDao.save(character)

    suspend fun deleteCharacter(characterId: Int) = characterDao.delete(characterId)

    suspend fun getMinCharacterId(): Int? = characterDao.getMinCharacterId()

    suspend fun getMaxCharacterId(): Int? = characterDao.getMaxCharacterId()


    fun getAllCharacters(): Flow<Resource<List<CharacterDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = characterApi.getAllCharacters()
            Log.d("CharacterNetRepository", "getAllCharacters: $users")
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}
