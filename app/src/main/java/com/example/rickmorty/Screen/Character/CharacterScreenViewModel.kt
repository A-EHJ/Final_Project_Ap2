package com.example.rickmorty.Screen.Character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.repository.Local.CharacterLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterBodyViewModel @Inject constructor(
    val characterLocalRepository: CharacterLocalRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterUIState())
    val uiState = _uiState.asStateFlow()

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val character = characterLocalRepository.getCharacterById(characterId)
            if (character != null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    character = character,
                    location = character.location_Id,
                    origen = character.origin_Id
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Character not found"
                )
            }

        }
    }
}

data class CharacterUIState(
    val isLoading: Boolean = false,
    val character: CharacterEntity = CharacterEntity(0, "", "", "", "", "", 0, 0, ""),
    val location: Int = 0,
    val origen: Int = 0,
    val errorMessage: String = ""
)
