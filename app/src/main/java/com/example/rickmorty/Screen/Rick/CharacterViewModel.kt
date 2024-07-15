package com.example.rickmorty.Screen.Rick

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.local.remote.Api.CharacterResponse
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
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCharacters()
        }
    }

    private fun getCharacters() {
        characterRepository.getAllCharacters().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            characters = result.data ?: CharacterResponse( info = InfoDto(0, 0, "", ""), results = emptyList())
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class CharacterUIState(
    val isLoading: Boolean = false,
    val characters: CharacterResponse = CharacterResponse( info = InfoDto(0, 0, "", ""), results = emptyList()),
    val errorMessage: String = ""
)
