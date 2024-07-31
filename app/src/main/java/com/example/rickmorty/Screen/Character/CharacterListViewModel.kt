package com.example.rickmorty.Screen.Character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.Util.DownloadData
import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.data.local.entities.LocationEntity
import com.example.rickmorty.data.repository.Local.CharacterLocalRepository
import com.example.rickmorty.data.repository.Local.LocationLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterLocalRepository: CharacterLocalRepository,
    private val locationLocalRepository: LocationLocalRepository,
    private val downloadData: DownloadData
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUIState())
    val uiState = _uiState.asStateFlow()
    private var minId = 0
    private var maxId = 0
    private var currentId = 0


    init {
        viewModelScope.launch {
            downloadData.downloadData()
            minId = characterLocalRepository.getMinCharacterId() ?: 0
            maxId = characterLocalRepository.getMaxCharacterId() ?: 0
            getCharacterLimited(minId)
        }
    }

    fun getCharacterLimited(isNextPage: Boolean) {
        if (_uiState.value.isLoading || _uiState.value.errorMessage != ""
            || (!_uiState.value.isPreviousPageAvailable && !isNextPage)
            || (!_uiState.value.isNextPageAvailable && isNextPage)
        ) {
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
        if (currentId < (minId + 0)) {
            _uiState.update { it.copy(isPreviousPageAvailable = false) }
        }
        if (currentId > (maxId - 10)) {
            _uiState.update { it.copy(isNextPageAvailable = false) }
        }

        viewModelScope.launch {
            getCharacterLimited(currentId)
        }
    }

    private suspend fun getCharacterLimited(startId: Int) {
        try {
            val character = characterLocalRepository.getCharactersLimited(startId)
            _uiState.update { it.copy(isLoading = false, characters = character) }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "An unexpected error occurred."
                )
            }
        }
    }

    private suspend fun getLocations() {
        try {
            val locations = locationLocalRepository.getLocations()
            _uiState.update { it.copy(locations = locations) }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: ".An unexpected error occurred."
                )
            }
        }
    }

    suspend fun getLocationById(locationId: Int): String {
        return locationLocalRepository.getLocationById(locationId)?.name ?: ""
    }

}

data class CharactersUIState(
    val isNextPageAvailable: Boolean = true,
    val isPreviousPageAvailable: Boolean = true,
    val isLoading: Boolean = false,
    val characters: List<CharacterEntity> = emptyList(),
    val locations: List<LocationEntity> = emptyList(),
    val errorMessage: String = ""
)
