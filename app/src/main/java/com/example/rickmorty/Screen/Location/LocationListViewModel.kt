package com.example.rickmorty.Screen.Location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.domain.models.LocationWithCharacterIdUrl
import com.example.rickmorty.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationListViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationUIState())
    val uiState = _uiState.asStateFlow()
    private var minId = 0
    private var maxId = 0
    private var currentId = 1
    private var minCurrentId = 0
    private var maxCurrentId = 0


    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isPreviousPageAvailable = false) }
            val minMaxIdFiltered = locationRepository.getMinMaxIdFiltered()
            minId = minMaxIdFiltered.minId ?: 0
            maxId = minMaxIdFiltered.maxId ?: 0
            val minMaxCurrentId = locationRepository.getMinMaxIdLimitedFiltered(minId)
            maxCurrentId = minMaxCurrentId.maxId ?: 0
            minCurrentId = minMaxCurrentId.minId ?: 0
            getLocationsLimited(minId)
        }
    }

    fun getLocationLimited() {
        _uiState.update { it.copy(isLoading = true) }
        currentId = minId
        viewModelScope.launch {
            locationRepository.getLocationsLimited(minId)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getCharacterLimited(isNextPage: Boolean) {
        if (_uiState.value.isLoading || _uiState.value.errorMessage != "" || (!_uiState.value.isPreviousPageAvailable && !isNextPage) || (!_uiState.value.isNextPageAvailable && isNextPage)) {
            return
        }

        _uiState.update {
            it.copy(
                isLoading = true, isPreviousPageAvailable = true, isNextPageAvailable = true
            )
        }

        currentId = if (isNextPage) {
            maxCurrentId + 1
        } else {
            minCurrentId
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
            val minMaxCurrentId = locationRepository.getMinMaxIdLimitedFiltered(currentId)
            maxCurrentId = minMaxCurrentId.maxId ?: 0
            minCurrentId = minMaxCurrentId.minId ?: 0
            val minMaxIdFiltered = locationRepository.getMinMaxIdFiltered()
            minId = minMaxIdFiltered.minId ?: 0
            maxId = minMaxIdFiltered.maxId ?: 0
            getLocationsLimited(currentId)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun getLocationsLimited(id: Int) {
        locationRepository.getLocationsLimited(id).let { locations ->
            _uiState.update {
                it.copy(
                    locationWithCharacterIdUrl = locations, isLoading = false, errorMessage = ""
                )
            }
        }
    }

}

data class LocationUIState(
    val isNextPageAvailable: Boolean = true,
    val isPreviousPageAvailable: Boolean = true,
    val isLoading: Boolean = false,
    val locationWithCharacterIdUrl: List<LocationWithCharacterIdUrl> = emptyList(),
    val errorMessage: String = "",
    val text: String = "",
)
