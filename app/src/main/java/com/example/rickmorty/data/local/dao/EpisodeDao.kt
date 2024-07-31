package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.rickmorty.data.local.entities.EpisodeEntity

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: EpisodeEntity)

    @Query("SELECT * FROM episode")
    fun getAllEpisodes(): List<EpisodeEntity>

    @Query(
        """
        SELECT * FROM episode
        WHERE name LIKE '%' || :query || '%'
        OR airdate LIKE '%' || :query || '%'
        OR episode LIKE '%' || :query || '%'
    """
    )
    suspend fun searchEpisodes(query: String): List<EpisodeEntity>

    @Query(
        """
        SELECT * FROM episode
        WHERE id = :episodeId
    """
    )
    suspend fun find(episodeId: Int): EpisodeEntity

    @Upsert
    suspend fun save(episode: EpisodeEntity)

    @Update
    suspend fun update(episode: EpisodeEntity)

    @Query("DELETE FROM episode WHERE id = :episodeId")
    suspend fun delete(episodeId: Int)

    @Query("SELECT * FROM episode WHERE id = :episodeId")
    suspend fun getEpisodeById(episodeId: Int): EpisodeEntity?
}
