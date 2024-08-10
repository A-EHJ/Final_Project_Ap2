package com.example.rickmorty.screen.location

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.rickmorty.domain.models.CharacterIIN
import com.example.rickmorty.domain.models.LocationWithCharacterIIN
import com.example.rickmorty.screen.character.bodyTextStyle
import com.example.rickmorty.screen.character.titleTextStyle
import com.example.rickmorty.screen.location.LocationScreenViewModel.LocationBodyUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    viewModel: LocationScreenViewModel = hiltViewModel(),
    locationId: Int,
    charactersId: List<Int>,
    onBack: () -> Unit,
    onCharacterClick: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = locationId) {
        viewModel.getLocation(locationId, charactersId)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        topBar = {
            TopAppBar(
                title = { Text("Location Details", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(39, 43, 51),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            LocationBody(innerPadding, uiState, onCharacterClick)
        }
    )
}

@Composable
fun LocationBody(
    innerPadding: PaddingValues,
    uiState: LocationBodyUIState,
    onCharacterClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(39, 43, 51),
                        Color(86, 98, 200)
                    )
                )
            )
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            uiState.errorMessage.isNotEmpty() -> {
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LocationDetail(uiState.locationWithCharacterIIN, onCharacterClick)
            }
        }
    }
}

@Composable
fun LocationDetail(location: LocationWithCharacterIIN, onCharacterClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = location.location.name,
            color = Color.White,
            style = titleTextStyle,
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(45, 88, 90),
                            Color(101, 71, 139)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LocationInfoItem("Type", location.location.type)
        LocationInfoItem("Dimension", location.location.dimension)
        CharacterGrid(characters = location.characters, onClick = onCharacterClick)
    }
}

@Composable
fun LocationInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", color = Color.Gray, style = bodyTextStyle)
        Text(text = value, color = Color.White, style = bodyTextStyle)
    }
}

@Composable
fun CharacterGrid(characters: List<CharacterIIN>, onClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(characters.size) { index ->
            val character = characters[index]
            CharacterItem(character, onClick)
        }
    }
}

@Composable
fun CharacterItem(characterIIN: CharacterIIN, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Gray.copy(alpha = 0.3f))
            .clickable { onClick(characterIIN.id) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = characterIIN.image,
            contentDescription = "Character Image",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Text(
            text = characterIIN.name,
            color = Color.White,
            style = titleTextStyle,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
