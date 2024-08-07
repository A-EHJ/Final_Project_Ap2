package com.example.rickmorty.screen.character

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
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    private val _characterBodyState = MutableStateFlow(CharacterBodyState())
    val uiState = _characterBodyState.asStateFlow()

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            _characterBodyState.value = _characterBodyState.value.copy(isLoading = true)
            val character = characterRepository.getCharacterById(characterId)
            _characterBodyState.value = _characterBodyState.value.copy(
                isLoading = false,
                character = character,
            )
        }
    }
}

data class CharacterBodyState(
    val isLoading: Boolean = false,
    val character: Character = Character(),
    val errorMessage: String = "",
)
