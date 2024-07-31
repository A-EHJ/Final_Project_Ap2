package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.rickmorty.data.local.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Query("SELECT * FROM location")
    fun getAllLocations(): Flow<List<LocationEntity>>

    @Query(
        """
        SELECT * FROM location
        WHERE name LIKE '%' || :query || '%'
        OR type LIKE '%' || :query || '%'
        OR dimension LIKE '%' || :query || '%'
    """
    )
    suspend fun searchLocations(query: String): List<LocationEntity>

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
