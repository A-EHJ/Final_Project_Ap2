package com.example.rickmorty.Screen.Rick

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rickmorty.data.local.remote.Api.CharacterResponse
import com.example.rickmorty.data.local.remote.dto.CharacterDto
import com.example.rickmorty.data.local.remote.dto.InfoDto
import com.example.rickmorty.ui.theme.RickMortyTheme
import coil.compose.AsyncImage
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterViewModel = hiltViewModel(),
    onCharacterClick: () -> Unit,
    onNextPageClick: () -> Unit,
    onPrevPageClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Navigation Menu",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = {}) {
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
                    onClick = onPrevPageClick,
                    containerColor = Color.Gray
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Previous Page")
                }
                FloatingActionButton(
                    onClick = onNextPageClick,
                    containerColor = Color.Gray
                ) {
                    Icon(Icons.Filled.ArrowForward, contentDescription = "Next Page")
                }
            }
        }
    ) { innerPadding ->
        CharacterBody(innerPadding, viewModel, uiState, onCharacterClick)
    }
}

@Composable
private fun CharacterBody(
    innerPadding: PaddingValues,
    viewModel: CharacterViewModel,
    uiState: CharacterUIState,
    onCharacterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(
                Brush.verticalGradient(
                    listOf
                        (
                        Color(39, 43, 51),
                        Color(86, 98, 200)
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.characters.results.size) { index ->
                    val character = uiState.characters.results[index]
                    CharacterItem(character, onCharacterClick)
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: CharacterDto, onCharacterClick: () -> Unit) {
    val titleTextStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
    val bodyTextStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.White
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCharacterClick() }
            .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.DarkGray.copy(alpha = 0.7f),
                        Color.Black.copy(alpha = 0.7f)
                    )
                )
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = "Character Image",
                modifier = Modifier
                    .size(80.dp) // Tamaño de la imagen
                    .clip(RoundedCornerShape(16.dp)) // Imagen redondeada
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Name: ${character.name}",
                    style = titleTextStyle
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(getStatusColor(character.status))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Status: ${character.status}",
                        style = bodyTextStyle
                    )
                }
                Text(
                    text = "Species: ${character.species}",
                    style = bodyTextStyle
                )
            }
        }
    }
}

@Composable
fun getStatusColor(status: String): Color {
    return when (status) {
        "Alive" -> Color.Green // Vivo (círculo verde)
        "unknown" -> Color.Yellow // Desconocido (círculo amarillo)
        "Dead" -> Color.Red // Muerto (círculo rojo)
        else -> Color.Gray
    }
}


@Preview
@Composable
private fun PreviewCharacterBody() {
    val characters = CharacterResponse(
        info = InfoDto(0, 0, "", ""),
        results = List(5) { index ->
            CharacterDto(
                id = index,
                name = "Character $index",
                status = "Status $index",
                species = "Species $index",
                type = "Type $index",
                gender = "Gender $index",
                origin = com.example.rickmorty.data.local.remote.dto.Origin("", ""),
                location = com.example.rickmorty.data.local.remote.dto.LocationNameUrl("", ""),
                image = "",
                episode = emptyList(),
                url = "",
                created = ""
            )
        }
    )

    RickMortyTheme {
        val viewModel: CharacterViewModel = hiltViewModel()
        CharacterBody(
            innerPadding = PaddingValues(16.dp),
            viewModel = viewModel,
            uiState = CharacterUIState(characters = characters),
            onCharacterClick = {}
        )
    }
}
