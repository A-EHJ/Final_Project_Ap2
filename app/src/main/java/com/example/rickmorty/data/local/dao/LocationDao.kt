package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.rickmorty.Util.MinMaxIdResult
import com.example.rickmorty.data.local.entities.LocationEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Query("SELECT * FROM location")
    fun getAllLocations(): List<LocationEntity>

    @Query(
        """
        SELECT * FROM location
        WHERE id >= :startId
        ORDER BY id ASC
        LIMIT 10
    """
    )
    suspend fun getLocationsLimited(startId: Int): List<LocationEntity>

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

    @Query(
        """
        SELECT * FROM location
        WHERE id = :locationId
    """
    )
    suspend fun find(locationId: Int): List<LocationEntity>

    @Upsert
    suspend fun save(location: LocationEntity)

    @Update
    suspend fun update(location: LocationEntity)

    @Query("DELETE FROM location WHERE id = :locationId")
    suspend fun delete(locationId: Int)

    @Query("SELECT * FROM location WHERE id = :locationId")
    suspend fun getLocationById(locationId: Int): LocationEntity?
}
