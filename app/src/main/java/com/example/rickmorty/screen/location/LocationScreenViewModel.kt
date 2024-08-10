package com.example.rickmorty.screen.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.local.entities.LocationEntity
import com.example.rickmorty.domain.models.LocationWithCharacterIIN
import com.example.rickmorty.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationScreenViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow((LocationBodyUIState()))
    val uiState = _uiState.asStateFlow()

    fun getLocation(locationId: Int, charactersId: List<Int>) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                locationWithCharacterIIN = locationRepository.getLocationById(
                    locationId,
                    charactersId
                )
            )
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    data class LocationBodyUIState(
        val isLoading: Boolean = false,
        val locationWithCharacterIIN: LocationWithCharacterIIN = LocationWithCharacterIIN(
            location = LocationEntity(
                0,
                "",
                "",
                ""
            ), emptyList()
        ),
        val errorMessage: String = "",
    )
}
