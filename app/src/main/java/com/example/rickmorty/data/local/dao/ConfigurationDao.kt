package com.example.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.rickmorty.data.local.entities.ConfigurationEntity

@Dao
interface ConfigurationDao {
    @Query("SELECT version FROM configuration order by id desc limit 1")
    suspend fun getVersion(): Int?

    @Upsert
    suspend fun saveVersion(configuration: ConfigurationEntity)
}
