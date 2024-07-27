package com.example.rickmorty.Screen.Rick

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.local.remote.Api.CharacterResponse
import com.example.rickmorty.data.local.remote.dto.CharacterDto
import com.example.rickmorty.data.local.remote.dto.InfoDto
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.rickmorty.data.local.repository.Resource
import com.example.rickmorty.data.local.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterBodyViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterUIState())
    val uiState = _uiState.asStateFlow()

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            characterRepository.getCharacterById(characterId)
                ?.let { character ->
                    _uiState.update { it.copy(character = character) }
                }
        }
    }

}

data class CharacterUIState(
    val isLoading: Boolean = false,
    val character: CharacterDto = CharacterDto(),
    val errorMessage: String = ""
)
