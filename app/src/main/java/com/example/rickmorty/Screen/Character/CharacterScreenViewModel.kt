package com.example.rickmorty.Screen.Character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.domain.models.Character
import com.example.rickmorty.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterBodyViewModel @Inject constructor(
    val characterRepository: CharacterRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val character = characterRepository.getCharacterById(characterId)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                character = character,
            )
        }
    }
}

data class UIState(
    val isLoading: Boolean = false,
    val character: Character = Character(),
    val errorMessage: String = "",
)
