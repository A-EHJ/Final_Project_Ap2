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
    suspend fun find(characterId: Int): CharacterEntity

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

    @Query("SELECT MIN(id) FROM character")
    suspend fun getMinCharacterId(): Int?

    @Query("SELECT MAX(id) FROM character")
    suspend fun getMaxCharacterId(): Int?
}
