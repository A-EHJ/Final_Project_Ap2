package com.example.rickmorty.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickmorty.data.local.dao.CharacterDao
import com.example.rickmorty.data.local.dao.CharacterEpisodeDao
import com.example.rickmorty.data.local.dao.ConfigurationDao
import com.example.rickmorty.data.local.dao.EpisodeDao
import com.example.rickmorty.data.local.dao.LocationDao
import com.example.rickmorty.data.local.dao.LocationResidentDao
import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.local.entities.CharacterEpisodeEntity
import com.example.rickmorty.data.local.entities.ConfigurationEntity
import com.example.rickmorty.data.local.entities.EpisodeEntity
import com.example.rickmorty.data.local.entities.LocationEntity
import com.example.rickmorty.data.local.entities.LocationResidentEntity

@Database(
    entities = [
        CharacterEntity::class,
        CharacterEpisodeEntity::class,
        EpisodeEntity::class,
        LocationEntity::class,
        LocationResidentEntity::class,
        ConfigurationEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class RMDb : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterEpisodeDao(): CharacterEpisodeDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun locationDao(): LocationDao
    abstract fun locationResidentDao(): LocationResidentDao
    abstract fun configurationDao(): ConfigurationDao
}
