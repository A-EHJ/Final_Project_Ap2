package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.util.MinMaxIdResult

@Dao
interface CharacterDao {
    @Upsert
    suspend fun save(character: CharacterEntity)

    @Query("SELECT * FROM character WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Int): CharacterEntity?

    @Query(
        """
        SELECT * FROM character
        WHERE id >= :startId
        ORDER BY id ASC
        LIMIT 10
    """
    )
    suspend fun getCharactersLimited(startId: Int): List<CharacterEntity>

    @Query(
        """
        SELECT * FROM character
        WHERE id >= :startId
        AND (name LIKE '%' || :text || '%')
        AND (status in (:statusAlive, :statusDead, :statusUnknown))
        AND (species in (:speciesHuman, :speciesCronenberg, :speciesDisease, :speciesPoopybutthole, :speciesAlien, :speciesUnknown, :speciesRobot, :speciesAnimal, :speciesMythologicalCreature, :speciesHumanoid))
        AND (gender in (:genderFemale, :genderMale, :genderGenderless, :genderUnknown))
        ORDER BY id ASC
        LIMIT 10
    """
    )
    suspend fun getCharactersLimitedFiltered(
        startId: Int,
        text: String,
        statusAlive: String,
        statusDead: String,
        statusUnknown: String,
        speciesHuman: String,
        speciesCronenberg: String,
        speciesDisease: String,
        speciesPoopybutthole: String,
        speciesAlien: String,
        speciesUnknown: String,
        speciesRobot: String,
        speciesAnimal: String,
        speciesMythologicalCreature: String,
        speciesHumanoid: String,
        genderFemale: String,
        genderMale: String,
        genderGenderless: String,
        genderUnknown: String,
    ): List<CharacterEntity>

    @Query(
        """
    SELECT MIN(id) FROM (
        SELECT id FROM character
        WHERE id < :startId
        AND (name LIKE '%' || :text || '%')
        AND (status IN (:statusAlive, :statusDead, :statusUnknown))
        AND (species IN (:speciesHuman, :speciesCronenberg, :speciesDisease, :speciesPoopybutthole, :speciesAlien, :speciesUnknown, :speciesRobot, :speciesAnimal, :speciesMythologicalCreature, :speciesHumanoid))
        AND (gender IN (:genderFemale, :genderMale, :genderGenderless, :genderUnknown))
        ORDER BY id DESC
        LIMIT 10
    )
    """
    )
    suspend fun getMinIdLimitedFiltered(
        startId: Int,
        text: String,
        statusAlive: String,
        statusDead: String,
        statusUnknown: String,
        speciesHuman: String,
        speciesCronenberg: String,
        speciesDisease: String,
        speciesPoopybutthole: String,
        speciesAlien: String,
        speciesUnknown: String,
        speciesRobot: String,
        speciesAnimal: String,
        speciesMythologicalCreature: String,
        speciesHumanoid: String,
        genderFemale: String,
        genderMale: String,
        genderGenderless: String,
        genderUnknown: String,
    ): Int?


    @Query(
        """
    SELECT MAX(id) FROM (
        SELECT id FROM character
        WHERE id >= :startId
        AND (name LIKE '%' || :text || '%')
        AND (status IN (:statusAlive, :statusDead, :statusUnknown))
        AND (species IN (:speciesHuman, :speciesCronenberg, :speciesDisease, :speciesPoopybutthole, :speciesAlien, :speciesUnknown, :speciesRobot, :speciesAnimal, :speciesMythologicalCreature, :speciesHumanoid))
        AND (gender IN (:genderFemale, :genderMale, :genderGenderless, :genderUnknown))
        ORDER BY id ASC
        LIMIT 10
    )
    """
    )
    suspend fun getMaxIdLimitedFiltered(
        startId: Int,
        text: String,
        statusAlive: String,
        statusDead: String,
        statusUnknown: String,
        speciesHuman: String,
        speciesCronenberg: String,
        speciesDisease: String,
        speciesPoopybutthole: String,
        speciesAlien: String,
        speciesUnknown: String,
        speciesRobot: String,
        speciesAnimal: String,
        speciesMythologicalCreature: String,
        speciesHumanoid: String,
        genderFemale: String,
        genderMale: String,
        genderGenderless: String,
        genderUnknown: String,
    ): Int?


    @Query(
        """
    SELECT MIN(id) AS minId, MAX(id) AS maxId FROM (
        SELECT id FROM character
        WHERE (name LIKE '%' || :text || '%')
        AND (status IN (:statusAlive, :statusDead, :statusUnknown))
        AND (species IN (:speciesHuman, :speciesCronenberg, :speciesDisease, :speciesPoopybutthole, :speciesAlien, :speciesUnknown, :speciesRobot, :speciesAnimal, :speciesMythologicalCreature, :speciesHumanoid))
        AND (gender IN (:genderFemale, :genderMale, :genderGenderless, :genderUnknown))
        ORDER BY id ASC
    )
    """
    )
    suspend fun getMinMaxIdFiltered(
        text: String,
        statusAlive: String,
        statusDead: String,
        statusUnknown: String,
        speciesHuman: String,
        speciesCronenberg: String,
        speciesDisease: String,
        speciesPoopybutthole: String,
        speciesAlien: String,
        speciesUnknown: String,
        speciesRobot: String,
        speciesAnimal: String,
        speciesMythologicalCreature: String,
        speciesHumanoid: String,
        genderFemale: String,
        genderMale: String,
        genderGenderless: String,
        genderUnknown: String,
    ): MinMaxIdResult?


    @Query("SELECT MIN(id) FROM character")
    suspend fun getMinCharacterId(): Int?

    @Query("SELECT MAX(id) FROM character")
    suspend fun getMaxCharacterId(): Int?
}
