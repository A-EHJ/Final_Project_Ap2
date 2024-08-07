package com.example.rickmorty.Screen.Character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.Util.DownloadData
import com.example.rickmorty.Util.MinMaxIdResult
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
    private var minCurrentId = 0
    private var maxCurrentId = 0


    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isPreviousPageAvailable = false) }
            downloadData.downloadAllData()
            minId = characterRepository.getMinCharacterId() ?: 0
            maxId = characterRepository.getMaxCharacterId() ?: 0
            val minMaxCurrentId = getMinMaxIdLimitedFiltered(minId)
            maxCurrentId = minMaxCurrentId.maxId ?: 0
            minCurrentId = minMaxCurrentId.minId ?: 0
            getCharactersLimited(minId)
        }
    }

    fun getCharacterLimited() {
        _uiState.update { it.copy(isLoading = true) }
        currentId = minId
        viewModelScope.launch {
            getCharactersLimitedFiltered(minId)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun downloadData() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            downloadData.downloadAllData()
            minId = characterRepository.getMinCharacterId() ?: 0
            maxId = characterRepository.getMaxCharacterId() ?: 0
            val minMaxCurrentId = getMinMaxIdLimitedFiltered(minId)
            maxCurrentId = minMaxCurrentId.maxId ?: 0
            minCurrentId = minMaxCurrentId.minId ?: 0
            getCharactersLimited(minId)
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
            val minMaxCurrentId = getMinMaxIdLimitedFiltered(currentId)
            maxCurrentId = minMaxCurrentId.maxId ?: 0
            minCurrentId = minMaxCurrentId.minId ?: 0
            val minMaxIdFiltered = getMinMaxIdFiltered()
            minId = minMaxIdFiltered.minId ?: 0
            maxId = minMaxIdFiltered.maxId ?: 0
            getCharactersLimitedFiltered(currentId)
            _uiState.update { it.copy(isLoading = false) }
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

    private suspend fun getCharactersLimitedFiltered(
        startId: Int,
    ) {
        characterRepository.getCharactersLimitedFiltered(
            startId = startId,
            text = _uiState.value.text,
            statusAlive = _uiState.value.statusAlive,
            statusDead = _uiState.value.statusDead,
            statusUnknown = _uiState.value.statusUnknown,
            speciesHuman = _uiState.value.speciesHuman,
            speciesCronenberg = _uiState.value.speciesCronenberg,
            speciesDisease = _uiState.value.speciesDisease,
            speciesPoopybutthole = _uiState.value.speciesPoopybutthole,
            speciesAlien = _uiState.value.speciesAlien,
            speciesUnknown = _uiState.value.speciesUnknown,
            speciesRobot = _uiState.value.speciesRobot,
            speciesAnimal = _uiState.value.speciesAnimal,
            speciesMythologicalCreature = _uiState.value.speciesMythologicalCreature,
            speciesHumanoid = _uiState.value.speciesHumanoid,
            genderFemale = _uiState.value.genderFemale,
            genderMale = _uiState.value.genderMale,
            genderGenderless = _uiState.value.genderGenderless,
            genderunknown = _uiState.value.genderunknown,
        ).let { characters ->
            _uiState.update {
                it.copy(
                    characters = characters, isLoading = false, errorMessage = ""
                )
            }
        }
    }

    private suspend fun getMinMaxIdLimitedFiltered(
        startId: Int,
    ): MinMaxIdResult {
        val minMaxid = characterRepository.getMinMaxIdLimitedFiltered(
            startId = startId,
            text = _uiState.value.text,
            statusAlive = _uiState.value.statusAlive,
            statusDead = _uiState.value.statusDead,
            statusUnknown = _uiState.value.statusUnknown,
            speciesHuman = _uiState.value.speciesHuman,
            speciesCronenberg = _uiState.value.speciesCronenberg,
            speciesDisease = _uiState.value.speciesDisease,
            speciesPoopybutthole = _uiState.value.speciesPoopybutthole,
            speciesAlien = _uiState.value.speciesAlien,
            speciesUnknown = _uiState.value.speciesUnknown,
            speciesRobot = _uiState.value.speciesRobot,
            speciesAnimal = _uiState.value.speciesAnimal,
            speciesMythologicalCreature = _uiState.value.speciesMythologicalCreature,
            speciesHumanoid = _uiState.value.speciesHumanoid,
            genderFemale = _uiState.value.genderFemale,
            genderMale = _uiState.value.genderMale,
            genderGenderless = _uiState.value.genderGenderless,
            genderunknown = _uiState.value.genderunknown,
        )
        var maxId = minMaxid.maxId
        var minId = minMaxid.minId
        if (maxId == null) {
            maxId = 0
        }
        if (minId == null) {
            minId = 0
        }
        val minMaxIdResult = MinMaxIdResult(minId = minId, maxId = maxId)

        return minMaxIdResult
    }

    private suspend fun getMinMaxIdFiltered(
    ): MinMaxIdResult {
        val minMaxid = characterRepository.getMinMaxIdFiltered(
            text = _uiState.value.text,
            statusAlive = _uiState.value.statusAlive,
            statusDead = _uiState.value.statusDead,
            statusUnknown = _uiState.value.statusUnknown,
            speciesHuman = _uiState.value.speciesHuman,
            speciesCronenberg = _uiState.value.speciesCronenberg,
            speciesDisease = _uiState.value.speciesDisease,
            speciesPoopybutthole = _uiState.value.speciesPoopybutthole,
            speciesAlien = _uiState.value.speciesAlien,
            speciesUnknown = _uiState.value.speciesUnknown,
            speciesRobot = _uiState.value.speciesRobot,
            speciesAnimal = _uiState.value.speciesAnimal,
            speciesMythologicalCreature = _uiState.value.speciesMythologicalCreature,
            speciesHumanoid = _uiState.value.speciesHumanoid,
            genderFemale = _uiState.value.genderFemale,
            genderMale = _uiState.value.genderMale,
            genderGenderless = _uiState.value.genderGenderless,
            genderunknown = _uiState.value.genderunknown,
        )
        var maxId = minMaxid?.maxId
        var minId = minMaxid?.minId
        if (maxId == null) {
            maxId = 0
        }
        if (minId == null) {
            minId = 0
        }
        val minMaxIdResult = MinMaxIdResult(minId = minId, maxId = maxId)

        return minMaxIdResult
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update { it.copy(text = text) }
    }

    fun onStatusAliveChecked() {
        _uiState.update { it.copy(statusAlive = !_uiState.value.statusAlive) }
    }

    fun onStatusDeadChecked() {
        _uiState.update { it.copy(statusDead = !_uiState.value.statusDead) }
    }

    fun onStatusUnknownChecked() {
        _uiState.update { it.copy(statusUnknown = !_uiState.value.statusUnknown) }
    }

    fun onSpeciesHumanChecked() {
        _uiState.update { it.copy(speciesHuman = !_uiState.value.speciesHuman) }
    }

    fun onSpeciesCronenbergChecked() {
        _uiState.update { it.copy(speciesCronenberg = !_uiState.value.speciesCronenberg) }
    }

    fun onSpeciesDiseaseChecked() {
        _uiState.update { it.copy(speciesDisease = !_uiState.value.speciesDisease) }
    }

    fun onSpeciesPoopybuttholeChecked() {
        _uiState.update { it.copy(speciesPoopybutthole = !_uiState.value.speciesPoopybutthole) }
    }

    fun onSpeciesAlienChecked() {
        _uiState.update { it.copy(speciesAlien = !_uiState.value.speciesAlien) }
    }

    fun onSpeciesUnknownChecked() {
        _uiState.update { it.copy(speciesUnknown = !_uiState.value.speciesUnknown) }
    }

    fun onSpeciesRobotChecked() {
        _uiState.update { it.copy(speciesRobot = !_uiState.value.speciesRobot) }
    }

    fun onSpeciesAnimalChecked() {
        _uiState.update { it.copy(speciesAnimal = !_uiState.value.speciesAnimal) }
    }

    fun onSpeciesMythologicalCreatureChecked() {
        _uiState.update { it.copy(speciesMythologicalCreature = !_uiState.value.speciesMythologicalCreature) }
    }

    fun onSpeciesHumanoidChecked() {
        _uiState.update { it.copy(speciesHumanoid = !_uiState.value.speciesHumanoid) }
    }

    fun onGenderFemaleChecked() {
        _uiState.update { it.copy(genderFemale = !_uiState.value.genderFemale) }
    }

    fun onGenderMaleChecked() {
        _uiState.update { it.copy(genderMale = !_uiState.value.genderMale) }
    }

    fun onGenderGenderlessChecked() {
        _uiState.update { it.copy(genderGenderless = !_uiState.value.genderGenderless) }
    }

    fun onGenderUnknownChecked() {
        _uiState.update { it.copy(genderunknown = !_uiState.value.genderunknown) }
    }

    fun resetFilters() {
        _uiState.update {
            it.copy(
                text = "",
                statusAlive = true,
                statusDead = true,
                statusUnknown = true,
                speciesHuman = true,
                speciesCronenberg = true,
                speciesDisease = true,
                speciesPoopybutthole = true,
                speciesAlien = true,
                speciesUnknown = true,
                speciesRobot = true,
                speciesAnimal = true,
                speciesMythologicalCreature = true,
                speciesHumanoid = true,
                genderFemale = true,
                genderMale = true,
                genderGenderless = true,
                genderunknown = true,
            )
        }
    }

    fun resetStatusFilters() {
        val bool =
            !(_uiState.value.statusAlive && _uiState.value.statusDead && _uiState.value.statusUnknown)
        _uiState.update {
            it.copy(
                statusAlive = bool,
                statusDead = bool,
                statusUnknown = bool,
            )
        }
    }

    fun resetSpeciesFilters() {
        val bool =
            !(_uiState.value.speciesHuman && _uiState.value.speciesCronenberg && _uiState.value.speciesDisease && _uiState.value.speciesPoopybutthole && _uiState.value.speciesAlien && _uiState.value.speciesUnknown && _uiState.value.speciesRobot && _uiState.value.speciesAnimal && _uiState.value.speciesMythologicalCreature && _uiState.value.speciesHumanoid)
        _uiState.update {
            it.copy(
                speciesHuman = bool,
                speciesCronenberg = bool,
                speciesDisease = bool,
                speciesPoopybutthole = bool,
                speciesAlien = bool,
                speciesUnknown = bool,
                speciesRobot = bool,
                speciesAnimal = bool,
                speciesMythologicalCreature = bool,
                speciesHumanoid = bool,
            )
        }
    }

    fun resetGenderFilters() {
        val bool =
            !(_uiState.value.genderFemale && _uiState.value.genderMale && _uiState.value.genderGenderless && _uiState.value.genderunknown)
        _uiState.update {
            it.copy(
                genderFemale = bool,
                genderMale = bool,
                genderGenderless = bool,
                genderunknown = bool,
            )
        }
    }

}

data class CharactersUIState(
    val isNextPageAvailable: Boolean = true,
    val isPreviousPageAvailable: Boolean = true,
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val errorMessage: String = "",
    val text: String = "",
    val statusAlive: Boolean = true,
    val statusDead: Boolean = true,
    val statusUnknown: Boolean = true,
    val speciesHuman: Boolean = true,
    val speciesCronenberg: Boolean = true,
    val speciesDisease: Boolean = true,
    val speciesPoopybutthole: Boolean = true,
    val speciesAlien: Boolean = true,
    val speciesUnknown: Boolean = true,
    val speciesRobot: Boolean = true,
    val speciesAnimal: Boolean = true,
    val speciesMythologicalCreature: Boolean = true,
    val speciesHumanoid: Boolean = true,
    val genderFemale: Boolean = true,
    val genderMale: Boolean = true,
    val genderGenderless: Boolean = true,
    val genderunknown: Boolean = true,
)
