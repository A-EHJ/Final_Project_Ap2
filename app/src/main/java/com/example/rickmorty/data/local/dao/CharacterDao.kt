package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.rickmorty.data.local.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)

    @Upsert
    suspend fun save(character: CharacterEntity)

    @Query("SELECT * FROM character")
    fun getAllCharacters(): List<CharacterEntity>

    @Query(
        """
        SELECT * FROM character
        WHERE name LIKE '%' || :query || '%'
        OR status LIKE '%' || :query || '%'
        OR species LIKE '%' || :query || '%'
        OR type LIKE '%' || :query || '%'
        """
    )
    suspend fun searchCharacters(query: String): List<CharacterEntity>

    @Query(
        """
        SELECT * FROM character
        WHERE id = :characterId
    """
    )
    fun find(characterId: Int): CharacterEntity

    @Update
    suspend fun update(character: CharacterEntity)

    @Query("DELETE FROM character WHERE id = :characterId")
    suspend fun delete(characterId: Int)

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
        SELECT MIN(id) FROM character
        WHERE (name LIKE '%' || :text || '%')
        AND (status in (:statusAlive, :statusDead, :statusUnknown))
        AND (species in (:speciesHuman, :speciesCronenberg, :speciesDisease, :speciesPoopybutthole, :speciesAlien, :speciesUnknown, :speciesRobot, :speciesAnimal, :speciesMythologicalCreature, :speciesHumanoid))
        AND (gender in (:genderFemale, :genderMale, :genderGenderless, :genderUnknown))
        ORDER BY id ASC
    """
    )
    suspend fun getMinIdFiltered(
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
        SELECT MAX(id) FROM character
        WHERE (name LIKE '%' || :text || '%')
        AND (status in (:statusAlive, :statusDead, :statusUnknown))
        AND (species in (:speciesHuman, :speciesCronenberg, :speciesDisease, :speciesPoopybutthole, :speciesAlien, :speciesUnknown, :speciesRobot, :speciesAnimal, :speciesMythologicalCreature, :speciesHumanoid))
        AND (gender in (:genderFemale, :genderMale, :genderGenderless, :genderUnknown))
        ORDER BY id ASC
        LIMIT 10
    """
    )
    suspend fun getMaxIdFiltered(
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


    @Query("SELECT MIN(id) FROM character")
    suspend fun getMinCharacterId(): Int?

    @Query("SELECT MAX(id) FROM character")
    suspend fun getMaxCharacterId(): Int?
}
