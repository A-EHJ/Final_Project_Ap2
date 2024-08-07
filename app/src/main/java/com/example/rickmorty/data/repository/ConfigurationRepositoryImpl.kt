package com.example.rickmorty.data.repository

import com.example.rickmorty.data.local.dao.ConfigurationDao
import com.example.rickmorty.data.local.entities.ConfigurationEntity
import com.example.rickmorty.data.network.remote.Api.ConfigurationApi
import javax.inject.Inject

class ConfigurationRepositoryImpl @Inject constructor(
    private val configurationDao: ConfigurationDao,
    private val configurationApi: ConfigurationApi,
) {
    suspend fun getVersionFromApi(): Int {
        return configurationApi.getAllVersion()
    }

    suspend fun upsertVersion(version: Int) {
        configurationDao.saveVersion(ConfigurationEntity(id = 0, version = version))
    }

    suspend fun getVersionFromDb(): Int? {
        val localVersion = configurationDao.getVersion()
        return localVersion
    }
}
