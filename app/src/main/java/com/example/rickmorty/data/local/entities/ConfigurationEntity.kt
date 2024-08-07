package com.example.rickmorty.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuration")
data class ConfigurationEntity(
    @PrimaryKey
    val id: Int,
    val version: Int,
)
