package com.example.rickmorty.screen.character

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.rickmorty.domain.models.Character


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
    onBack: () -> Unit,
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
            CharacterBody(innerPadding, uiState)
        }
    )
}

@Composable
private fun CharacterBody(
    innerPadding: PaddingValues,
    characterBodyState: CharacterBodyState,
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
            characterBodyState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            characterBodyState.errorMessage.isNotEmpty() -> {
                Text(
                    text = characterBodyState.errorMessage,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                CharacterDetail(characterBodyState.character)
            }
        }
    }
}

@Composable
fun CharacterDetail(character: Character) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = character.character.image,
            contentDescription = "Character Image",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
                .background(Color.Gray.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = character.character.name,
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
        CharacterStatusItem("Status", character.character.status)
        CharacterInfoItem("Species", character.character.species)
        if (character.character.type.isNotEmpty()) {
            CharacterInfoItem("Type", character.character.type)
        }
        CharacterInfoItem("Gender", character.character.gender)
        if (character.origin == "") {
            CharacterInfoItem("Origin", "Unknown")

        } else {
            CharacterInfoItem("Origin", character.origin)
        }
        CharacterInfoItem("Last known location", character.location)
    }
}

@Composable
fun CharacterStatusItem(label: String, value: String) {

    val statusColor = GetStatusColor(value)
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
