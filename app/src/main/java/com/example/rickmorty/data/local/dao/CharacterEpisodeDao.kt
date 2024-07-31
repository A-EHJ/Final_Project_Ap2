package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.rickmorty.data.local.entities.CharacterEpisodeEntity

@Dao
interface CharacterEpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterEpisode: CharacterEpisodeEntity)

    @Query("SELECT * FROM character_episode")
    suspend fun getAllCharacterEpisodes(): List<CharacterEpisodeEntity>

    @Query(
        """
        SELECT * FROM character_episode
        WHERE characterId = :characterId
    """
    )
    suspend fun findCharacter(characterId: Int): List<CharacterEpisodeEntity>

    @Query(
        """
        SELECT * FROM character_episode
        WHERE episodeId = :episodeId
    """
    )
    suspend fun findEpisode(episodeId: Int): List<CharacterEpisodeEntity>

    @Upsert
    suspend fun save(characterEpisode: CharacterEpisodeEntity)

    @Update
    suspend fun update(characterEpisode: CharacterEpisodeEntity)

    @Delete
    suspend fun delete(characterEpisode: CharacterEpisodeEntity)

    @Query("SELECT * FROM character_episode WHERE characterId = :characterId AND episodeId = :episodeId")
    suspend fun getCharacterEpisode(characterId: Int, episodeId: Int): CharacterEpisodeEntity?
}
