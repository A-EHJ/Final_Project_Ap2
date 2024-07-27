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
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUIState())
    val uiState = _uiState.asStateFlow()



    init {
        viewModelScope.launch {
            getCharacters(initial = true, nextPage = false)
        }
    }

    fun getCharacters(nextPage: Boolean) {
        viewModelScope.launch {
            getCharacters(initial = false, nextPage = nextPage)
        }
    }



    private fun getCharacters(initial: Boolean, nextPage: Boolean) {
        var page = 1
        if (!initial) {
            if (nextPage) {
                if (uiState.value.characters.info.next != null) {
                    page =  extractPageNumber(uiState.value.characters.info.next!!) ?: 12
                }
            } else {
                if (uiState.value.characters.info.prev != null) {
                    page = extractPageNumber(uiState.value.characters.info.prev!!) ?: 10
                }
            }
        }
        characterRepository.getAllCharacters(page).onEach { result ->
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

    private fun extractPageNumber(url: String): Int? {
        return try {
            // Intenta extraer el número de página usando una expresión regular
            val regex = "page=(\\d+)".toRegex()
            val matchResult = regex.find(url)
            matchResult?.groupValues?.get(1)?.toInt()
        } catch (e: Exception) {
            return 2
        }
    }

    fun getCharacterById(characterId: Int) {
        viewModelScope.launch {
            characterRepository.getCharacterById(characterId)
        }
    }

}

data class CharactersUIState(
    val isLoading: Boolean = false,
    val characters: CharacterResponse = CharacterResponse( info = InfoDto(0, 0, "", ""), results = emptyList()),
    val errorMessage: String = ""
)
