package com.example.rickmorty.Screen.Character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.Util.DownloadData
import com.example.rickmorty.domain.models.Character
import com.example.rickmorty.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val downloadData: DownloadData,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUIState())
    val uiState = _uiState.asStateFlow()
    private var minId = 0
    private var maxId = 0
    private var currentId = 1


    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            downloadData.downloadAllData()
            minId = characterRepository.getMinCharacterId() ?: 0
            maxId = characterRepository.getMaxCharacterId() ?: 0
            getCharactersLimited(minId)
            
        }
    }

    fun getCharacterLimited(isNextPage: Boolean) {
        if (_uiState.value.isLoading || _uiState.value.errorMessage != "" || (!_uiState.value.isPreviousPageAvailable && !isNextPage) || (!_uiState.value.isNextPageAvailable && isNextPage)) {
            return
        }

        if (isNextPage) {
            currentId += 10
        } else {
            currentId -= 10
        }
        if (currentId < minId) {
            currentId = minId
        } else if (currentId > maxId) {
            currentId = maxId
        }
        if (currentId < (minId + 9)) {
            _uiState.update { it.copy(isPreviousPageAvailable = false) }
        }
        if (currentId > (maxId - 10)) {
            _uiState.update { it.copy(isNextPageAvailable = false) }
        }

        viewModelScope.launch {
            getCharactersLimited(currentId)
        }
    }

    private suspend fun getCharactersLimited(id: Int) {
        characterRepository.getCharactersLimited(id).let { characters ->
            _uiState.update {
                it.copy(
                    characters = characters, isLoading = false, errorMessage = ""
                )
            }
        }
    }
}

data class CharactersUIState(
    val isNextPageAvailable: Boolean = true,
    val isPreviousPageAvailable: Boolean = true,
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val errorMessage: String = "",
)
