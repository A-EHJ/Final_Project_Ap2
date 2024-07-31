package com.example.rickmorty.data.repository.Net

import android.util.Log
import com.example.rickmorty.data.Network.remote.Api.EpisodeApi
import com.example.rickmorty.data.Network.remote.dto.EpisodeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EpisodeNetRepository @Inject constructor(
    private val episodeApi: EpisodeApi
) {
    fun getAllEpisodes(): Flow<Resource<List<EpisodeDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = episodeApi.getAllEpisodes()
            Log.d("EpisodeNetRepository", "getAllEpisodes: $users")
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}
