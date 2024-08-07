package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.rickmorty.data.local.entities.LocationResidentEntity

@Dao
interface LocationResidentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationResident: LocationResidentEntity)

    @Upsert
    suspend fun save(locationResident: LocationResidentEntity)

    @Query("SELECT * FROM location_resident")
    suspend fun getAllLocationResidents(): List<LocationResidentEntity>

    @Query(
        """
        SELECT CharacterId FROM location_resident
        WHERE locationid = :locationId
    """
    )
    suspend fun findLocation(locationId: Int): List<Int>

    @Query(
        """
        SELECT * FROM location_resident
        WHERE characterid = :characterId
    """
    )
    suspend fun findCharacter(characterId: Int): List<LocationResidentEntity>

    @Update
    suspend fun update(locationResident: LocationResidentEntity)

    @Delete
    suspend fun delete(locationResident: LocationResidentEntity)

    @Query("SELECT * FROM location_resident WHERE locationid = :locationId AND characterid = :characterId")
    suspend fun getLocationResident(locationId: Int, characterId: Int): LocationResidentEntity?
}
