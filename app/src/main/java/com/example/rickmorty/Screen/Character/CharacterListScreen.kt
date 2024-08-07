package com.example.rickmorty.Screen.Character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.domain.models.Character
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    onCharacterClick: (Int) -> Unit,
    drawer: DrawerState,
    onPreviousPageClick: () -> Unit = { viewModel.getCharacterLimited(false) },
    onNextPageClick: () -> Unit = { viewModel.getCharacterLimited(true) },
    onGetCharacterLimitedFiltered: () -> Unit = { viewModel.getCharacterLimited() },
    resetFilters: () -> Unit = { viewModel.resetFilters() },
    onTextChange: (String) -> Unit = { viewModel.onSearchTextChanged(it) },
    onStatusAliveChange: () -> Unit = { viewModel.onStatusAliveChecked() },
    onStatusDeadChange: () -> Unit = { viewModel.onStatusDeadChecked() },
    onStatusUnknownChange: () -> Unit = { viewModel.onStatusUnknownChecked() },
    onSpeciesHumanChange: () -> Unit = { viewModel.onSpeciesHumanChecked() },
    onSpeciesCronenbergChange: () -> Unit = { viewModel.onSpeciesCronenbergChecked() },
    onSpeciesDiseaseChange: () -> Unit = { viewModel.onSpeciesDiseaseChecked() },
    onSpeciesPoopybuttholeChange: () -> Unit = { viewModel.onSpeciesPoopybuttholeChecked() },
    onSpeciesAlienChange: () -> Unit = { viewModel.onSpeciesAlienChecked() },
    onSpeciesUnknownChange: () -> Unit = { viewModel.onSpeciesUnknownChecked() },
    onSpeciesRobotChange: () -> Unit = { viewModel.onSpeciesRobotChecked() },
    onSpeciesAnimalChange: () -> Unit = { viewModel.onSpeciesAnimalChecked() },
    onSpeciesMythologicalCreatureChange: () -> Unit = {
        viewModel.onSpeciesMythologicalCreatureChecked()
    },
    onSpeciesHumanoidChange: () -> Unit = { viewModel.onSpeciesHumanoidChecked() },
    onGenderFemaleChange: () -> Unit = { viewModel.onGenderFemaleChecked() },
    onGenderMaleChange: () -> Unit = { viewModel.onGenderMaleChecked() },
    onGenderGenderlessChange: () -> Unit = { viewModel.onGenderGenderlessChecked() },
    onGenderUnknownChange: () -> Unit = { viewModel.onGenderUnknownChecked() },
    onResetStatusFilters: () -> Unit = { viewModel.resetStatusFilters() },
    onResetSpeciesFilters: () -> Unit = { viewModel.resetSpeciesFilters() },
    onResetGenderFilters: () -> Unit = { viewModel.resetGenderFilters() },
    onDownloadData: () -> Unit = { viewModel.downloadData() },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val unShowBottomSheet: () -> Unit = {
        showBottomSheet = false
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        topBar = {
            TopAppBar(
                title = { Text("Character List", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(39, 43, 51),
                    titleContentColor = Color.White
                ),
                actions = {
                    Row {
                        IconButton(onClick = {
                            scope.launch { drawer.open() }
                        }) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Navigation Menu",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { showBottomSheet = !showBottomSheet }) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            ) {
                FloatingActionButton(
                    onClick = onPreviousPageClick,
                    containerColor = if (uiState.isPreviousPageAvailable) Color(
                        142,
                        102,
                        193
                    ) else Color.Gray,
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Page")
                }
                FloatingActionButton(
                    onClick = onNextPageClick,
                    containerColor = if (uiState.isNextPageAvailable) Color(
                        142,
                        102,
                        193
                    ) else Color.Gray,
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Page")
                }
            }
        }
    ) { innerPadding ->
        CharacterListBody(innerPadding, uiState, onCharacterClick, onDownloadData)
        if (showBottomSheet) {
            ModalCharacterBottomSheet(
                unShowBottomSheet,
                sheetState,
                uiState,
                onTextChange,
                scope,
                onGetCharacterLimitedFiltered,
                resetFilters,
                onResetStatusFilters,
                onStatusAliveChange,
                onStatusDeadChange,
                onStatusUnknownChange,
                onResetSpeciesFilters,
                onSpeciesHumanChange,
                onSpeciesCronenbergChange,
                onSpeciesDiseaseChange,
                onSpeciesPoopybuttholeChange,
                onSpeciesAlienChange,
                onSpeciesUnknownChange,
                onSpeciesRobotChange,
                onSpeciesMythologicalCreatureChange,
                onSpeciesAnimalChange,
                onSpeciesHumanoidChange,
                onResetGenderFilters,
                onGenderFemaleChange,
                onGenderMaleChange,
                onGenderGenderlessChange,
                onGenderUnknownChange
            )
        }
    }
}

@Composable
private fun CharacterListBody(
    innerPadding: PaddingValues,
    uiState: CharactersUIState,
    onCharacterClick: (Int) -> Unit,
    onDownloadData: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(39, 43, 51), Color(86, 98, 200)
                    )
                )
            )
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (uiState.errorMessage.isNotEmpty() || uiState.characters.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "An error occurred",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { onDownloadData() }) {
                        Text("Retry")
                    }
                }
            }

        } else {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.characters.size) { index ->
                    val character = uiState.characters[index]
                    CharacterItem(character, onCharacterClick)
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewCharacterItem() {
    CharacterItem(character = Character(
        character = CharacterEntity(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin_Id = 1,
            location_Id = 20,
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        ), location = "Earth (Replacement Dimension)", origin = "Earth (C-137)"
    ), onCharacterClick = {})
}
