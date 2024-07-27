package com.example.rickmorty.Screen.Rick

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.rickmorty.data.local.remote.dto.CharacterDto

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    viewModel: CharacterBodyViewModel = hiltViewModel(),
    characterId: Int,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = characterId) {
        viewModel.getCharacter(characterId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Character Details", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(39, 43, 51),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { innerPadding ->
            CharacterBody(innerPadding, uiState)
        }
    )
}

@Composable
private fun CharacterBody(
    innerPadding: PaddingValues,
    uiState: CharacterUIState
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
                uiState.character.let { character ->
                    CharacterDetail(character)
                }
            }
        }
    }
}

@Composable
fun CharacterDetail(character: CharacterDto) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = "Character Image",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
                .background(Color.Gray.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = character.name,
            color = Color.White,
            style = titleTextStyle,
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(86, 98, 200), Color(39, 43, 51))
                    )
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        CharacterStatusItem("Status", character.status)
        CharacterInfoItem("Species", character.species)
        CharacterInfoItem("Type", character.type)
        CharacterInfoItem("Gender", character.gender)
        CharacterInfoItem("Origin", character.origin.name)
        CharacterInfoItem("Last known location", character.location.name)
    }
}

@Composable
fun CharacterStatusItem(label: String, value: String) {

    val statusColor = when (value.lowercase()) {
        "alive" -> Color.Green
        "dead" -> Color.Red
        else -> Color.Gray
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$label:", color = Color.Gray, style = bodyTextStyle)
        Text(
            text = value,
            color = statusColor,
            style = bodyTextStyle,
            modifier = Modifier
                .background(statusColor.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun CharacterInfoItem(label: String, value: String) {
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
