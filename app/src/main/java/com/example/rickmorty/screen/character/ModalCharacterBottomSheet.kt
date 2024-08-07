package com.example.rickmorty.screen.character

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rickmorty.domain.models.Character
import com.example.rickmorty.screen.component.CustomFilterChip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ModalCharacterBottomSheet(
    unShowBottomSheet: () -> Unit,
    sheetState: SheetState,
    uiState: CharactersUIState,
    onTextChange: (String) -> Unit,
    scope: CoroutineScope,
    onGetCharacterLimitedFiltered: () -> Unit,
    resetFilters: () -> Unit,
    onResetStatusFilters: () -> Unit,
    onStatusAliveChange: () -> Unit,
    onStatusDeadChange: () -> Unit,
    onStatusUnknownChange: () -> Unit,
    onResetSpeciesFilters: () -> Unit,
    onSpeciesHumanChange: () -> Unit,
    onSpeciesCronenbergChange: () -> Unit,
    onSpeciesDiseaseChange: () -> Unit,
    onSpeciesPoopybuttholeChange: () -> Unit,
    onSpeciesAlienChange: () -> Unit,
    onSpeciesUnknownChange: () -> Unit,
    onSpeciesRobotChange: () -> Unit,
    onSpeciesMythologicalCreatureChange: () -> Unit,
    onSpeciesAnimalChange: () -> Unit,
    onSpeciesHumanoidChange: () -> Unit,
    onResetGenderFilters: () -> Unit,
    onGenderFemaleChange: () -> Unit,
    onGenderMaleChange: () -> Unit,
    onGenderGenderlessChange: () -> Unit,
    onGenderUnknownChange: () -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = {
            unShowBottomSheet()
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
                    contentDescription = "Search",
                    modifier = Modifier.clickable {
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    unShowBottomSheet()
                                }
                            }
                        onGetCharacterLimitedFiltered()
                    }
                )
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        unShowBottomSheet()
                    }
                }
            }) {
                Text("Cancel")
                Icon(Icons.Default.Close, contentDescription = "Cancel")
            }
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        unShowBottomSheet()
                    }
                }
                onGetCharacterLimitedFiltered()
            }) {
                Text("Filter")
                Icon(Icons.Default.Search, contentDescription = "SearchFiltered")
            }
            Button(onClick = {
                resetFilters()
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        unShowBottomSheet()
                    }
                }
                onGetCharacterLimitedFiltered()
            }) {
                Text("Reset")
                Icon(Icons.Default.Refresh, contentDescription = "Reset")
            }
            Spacer(modifier = Modifier.width(16.dp))
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
                    selected = uiState.speciesAlien,
                    onSelectedChange = { onSpeciesAlienChange() },
                    text = "Alien"
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomFilterChip(
                    selected = uiState.speciesHumanoid,
                    onSelectedChange = { onSpeciesHumanoidChange() },
                    text = "Humanoid"
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
                    selected = uiState.speciesPoopybutthole,
                    onSelectedChange = { onSpeciesPoopybuttholeChange() },
                    text = "Poopybutthole"
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
                        onResetGenderFilters()
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
                Text(
                    text = "Species: ${character.character.species}", style = bodyTextStyle
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Gender: ${character.character.gender}", style = bodyTextStyle
                )
                Text(
                    text = "Last known Location: ${if (character.location == "") character.location else "Unknown"}",
                    style = bodyTextStyle
                )
                Text(
                    text = "First seen: ${if (character.origin == "") character.origin else "Unknown"}",
                    style = bodyTextStyle
                )
            }
        }
    }
}
