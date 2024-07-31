package com.example.rickmorty.data.repository.Local

import com.example.rickmorty.data.local.dao.CharacterDao
import com.example.rickmorty.data.local.entities.CharacterEntity
import javax.inject.Inject

class CharacterLocalRepository @Inject constructor(private val characterDao: CharacterDao) {
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
}
