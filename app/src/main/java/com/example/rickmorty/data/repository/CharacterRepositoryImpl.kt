package com.example.rickmorty.data.repository

import android.util.Log
import com.example.rickmorty.data.local.dao.CharacterDao
import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.network.remote.Api.CharacterApi
import com.example.rickmorty.data.network.remote.dto.CharacterDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterApi: CharacterApi
) {
    fun getCharacters() = characterDao.getAllCharacters()

    suspend fun getCharacterById(id: Int) = characterDao.getCharacterById(id)

    suspend fun insertCharacters(characters: List<CharacterEntity>) {
        characters.forEach { character ->
            characterDao.save(character)
        }
    }

    suspend fun getCharactersLimited(startId: Int) = characterDao.getCharactersLimited(startId)

    suspend fun insertCharacter(character: CharacterEntity) = characterDao.save(character)

    suspend fun deleteCharacter(characterId: Int) = characterDao.delete(characterId)

    suspend fun getMinCharacterId(): Int? = characterDao.getMinCharacterId()

    suspend fun getMaxCharacterId(): Int? = characterDao.getMaxCharacterId()


    fun getAllCharacters(): Flow<Resource<List<CharacterDto>>> = flow {
        emit(Resource.Loading())
        try {
            val users = characterApi.getAllCharacters()
            Log.d("CharacterNetRepository", "getAllCharacters: $users")
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}
