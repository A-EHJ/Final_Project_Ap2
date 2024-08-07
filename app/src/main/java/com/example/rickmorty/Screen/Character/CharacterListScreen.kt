package com.example.rickmorty.Screen.Character

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults.filterChipColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.rickmorty.data.local.entities.CharacterEntity
import com.example.rickmorty.domain.models.Character
import kotlinx.coroutines.launch

@Composable
fun CustomFilterChip(
    selected: Boolean,
    onSelectedChange: () -> Unit,
    text: String,
) {
    FilterChip(selected = selected, onClick = { onSelectedChange() }, label = {
        Text(text)
    }, colors = filterChipColors()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    onCharacterClick: (Int) -> Unit,
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

    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

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
        CharacterListBody(innerPadding, uiState, onCharacterClick)
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {

                TextField(
                    value = uiState.text,
                    onValueChange = { onTextChange(it) },
                    label = { Text("Label") },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Row {
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                            viewModel.getCharacterLimited(true)
                        }
                    }) {
                        Text("Filter")
                    }
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                            resetFilters()
                            viewModel.getCharacterLimited(true)
                        }
                    }) {
                        Text("Reset")
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        "Filters",
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    HorizontalDivider(thickness = 3.dp)
                    Text(
                        "Status",
                        modifier = Modifier
                            .padding(10.dp)
                            .widthIn(10.dp, 100.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                onResetStatusFilters()
                            }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CustomFilterChip(
                            selected = uiState.statusAlive,
                            onSelectedChange = { onStatusAliveChange() },
                            text = "Alive"
                        )
                        CustomFilterChip(
                            selected = uiState.statusDead,
                            onSelectedChange = { onStatusDeadChange() },
                            text = "Dead"
                        )
                        CustomFilterChip(
                            selected = uiState.statusUnknown,
                            onSelectedChange = { onStatusUnknownChange() },
                            text = "Unknown"
                        )
                    }

                    HorizontalDivider(thickness = 1.dp)

                    Text(
                        "Species",
                        modifier = Modifier
                            .padding(10.dp)
                            .widthIn(10.dp, 100.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                onResetSpeciesFilters()
                            }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CustomFilterChip(
                            selected = uiState.speciesHuman,
                            onSelectedChange = { onSpeciesHumanChange() },
                            text = "Human"
                        )
                        CustomFilterChip(
                            selected = uiState.speciesCronenberg,
                            onSelectedChange = { onSpeciesCronenbergChange() },
                            text = "Cronenberg"
                        )
                        CustomFilterChip(
                            selected = uiState.speciesDisease,
                            onSelectedChange = { onSpeciesDiseaseChange() },
                            text = "Disease"
                        )
                        CustomFilterChip(
                            selected = uiState.speciesPoopybutthole,
                            onSelectedChange = { onSpeciesPoopybuttholeChange() },
                            text = "Poopybutthole"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CustomFilterChip(
                            selected = uiState.speciesAlien,
                            onSelectedChange = { onSpeciesAlienChange() },
                            text = "Alien"
                        )
                        CustomFilterChip(
                            selected = uiState.speciesUnknown,
                            onSelectedChange = { onSpeciesUnknownChange() },
                            text = "Unknown"
                        )
                        CustomFilterChip(
                            selected = uiState.speciesRobot,
                            onSelectedChange = { onSpeciesRobotChange() },
                            text = "Robot"
                        )
                        CustomFilterChip(
                            selected = uiState.speciesAnimal,
                            onSelectedChange = { onSpeciesAnimalChange() },
                            text = "Animal"
                        )

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CustomFilterChip(
                            selected = uiState.speciesMythologicalCreature,
                            onSelectedChange = { onSpeciesMythologicalCreatureChange() },
                            text = "Mythological Creature"
                        )
                        CustomFilterChip(
                            selected = uiState.speciesHumanoid,
                            onSelectedChange = { onSpeciesHumanoidChange() },
                            text = "Mythological Creature"
                        )
                        CustomFilterChip(
                            selected = uiState.speciesHumanoid,
                            onSelectedChange = { onSpeciesHumanoidChange() },
                            text = "Humanoid"
                        )
                    }

                    HorizontalDivider(thickness = 1.dp)

                    Text(
                        "Gender",
                        modifier = Modifier
                            .padding(10.dp)
                            .widthIn(10.dp, 100.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                            }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CustomFilterChip(
                            selected = uiState.genderFemale,
                            onSelectedChange = { onGenderFemaleChange() },
                            text = "Female"
                        )
                        CustomFilterChip(
                            selected = uiState.genderMale,
                            onSelectedChange = { onGenderMaleChange() },
                            text = "Male"
                        )
                        CustomFilterChip(
                            selected = uiState.genderGenderless,
                            onSelectedChange = { onGenderGenderlessChange() },
                            text = "Genderless"
                        )
                        CustomFilterChip(
                            selected = uiState.genderunknown,
                            onSelectedChange = { onGenderUnknownChange() },
                            text = "Unknown"
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun CharacterListBody(
    innerPadding: PaddingValues,
    uiState: CharactersUIState,
    onCharacterClick: (Int) -> Unit,
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
        } else if (uiState.errorMessage.isNotEmpty()) {
            Text(
                text = uiState.errorMessage,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
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


@Composable
fun CharacterItem(character: Character, onCharacterClick: (Int) -> Unit) {
    val titleTextStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White
    )
    val bodyTextStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color.White
    )

    val borderColor = getStatusColor(character.character.status)

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onCharacterClick(character.character.id) }
        .clip(RoundedCornerShape(16.dp)) // Rounded corners
        .border(2.dp, borderColor, RoundedCornerShape(16.dp)) // Border based on status
        .background(
            Brush.verticalGradient(
                colors = listOf(
                    Color.DarkGray.copy(alpha = 0.7f), Color.Black.copy(alpha = 0.7f)
                )
            )
        )) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = character.character.image,
                contentDescription = "Character Image",
                modifier = Modifier
                    .size(80.dp) // Image size
                    .clip(RoundedCornerShape(16.dp)) // Rounded image
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Name: ${character.character.name}", style = titleTextStyle
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(getStatusColor(character.character.status))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Status: ${character.character.status}", style = bodyTextStyle
                    )
                }

                Row {
                    Text(
                        text = "Species: ${character.character.species}", style = bodyTextStyle
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Gender: ${character.character.gender}", style = bodyTextStyle
                    )

                }
                Text(
                    text = "Last known Location: ${character.location}", style = bodyTextStyle
                )
                Text(
                    text = "First seen: ${character.origin}", style = bodyTextStyle
                )
            }
        }
    }
}

@Composable
fun getStatusColor(status: String): Color {
    return when (status) {
        "Alive" -> Color.Green // Alive (green circle)
        "unknown" -> Color.Yellow // Unknown (yellow circle)
        "Dead" -> Color.Red // Dead (red circle)
        else -> Color.Gray
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
