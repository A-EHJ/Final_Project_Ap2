package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.rickmorty.data.local.entities.LocationEntity
import com.example.rickmorty.util.MinMaxIdResult

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    fun getAllLocations(): List<LocationEntity>

    @Query(
        """
        SELECT * FROM location
        WHERE id >= :startId
        AND name LIKE '%' || :text || '%'
        OR type LIKE '%' || :text || '%'
        OR dimension LIKE '%' || :text || '%'
        ORDER BY id ASC
        LIMIT 10
    """
    )
    suspend fun getLocationsLimited(startId: Int, text: String): List<LocationEntity>

    @Query(
        """
    SELECT MIN(id) FROM (
        SELECT id FROM location
        WHERE id < :startId
        ORDER BY id DESC
        LIMIT 10
    )
    """
    )
    suspend fun getMinIdLimitedFiltered(
        startId: Int,
    ): Int?


    @Query(
        """
    SELECT MAX(id) FROM (
        SELECT id FROM character
        WHERE id >= :startId
        ORDER BY id ASC
        LIMIT 10
    )
    """
    )
    suspend fun getMaxIdLimitedFiltered(
        startId: Int,
    ): Int?

    @Query(
        """
    SELECT MIN(id) AS minId, MAX(id) AS maxId FROM (
        SELECT id FROM character
        ORDER BY id ASC
    )
    """
    )
    suspend fun getMinMaxIdFiltered(
    ): MinMaxIdResult?

    @Upsert
    suspend fun save(location: LocationEntity)

    @Query("SELECT * FROM location WHERE id = :locationId")
    suspend fun getLocationById(locationId: Int): LocationEntity?
}
