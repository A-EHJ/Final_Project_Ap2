package com.example.rickmorty.screen.location

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.rickmorty.domain.models.LocationWithCharacterIdImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(
    viewModel: LocationListViewModel = hiltViewModel(),
    onLocationClick: (id: Int, characters: List<Int>) -> Unit,
    drawer: DrawerState,
    onPreviousPageClick: () -> Unit = { viewModel.getCharacterLimited(false) },
    onNextPageClick: () -> Unit = { viewModel.getCharacterLimited(true) },
    onTextChange: (String) -> Unit = { viewModel.onSearchTextChanged(it) },
    onResetFilters: () -> Unit = { viewModel.resetFilters() },
    onGetCharacterLimitedFiltered: () -> Unit = { viewModel.getLocationsLimitedFiltered() },
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
                title = { Text("Location List", color = Color.White) },
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
        LocationListBody(innerPadding, uiState, onLocationClick)
        if (showBottomSheet) {
            ModalLocationBottonSheet(
                unShowBottomSheet,
                onGetCharacterLimitedFiltered,
                onTextChange,
                onResetFilters,
                sheetState,
                uiState,
                scope
            )
        }
    }
}

@Composable
fun LocationListBody(
    innerPadding: PaddingValues,
    uiState: LocationUIState,
    onCharacterClick: (Int, List<Int>) -> Unit,
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
        } else if (uiState.errorMessage.isNotEmpty() || uiState.locationWithCharacterIdImage.isEmpty()) {
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
                    Button(onClick = { }) {
                        Text("Retry")
                    }
                }
            }

        } else {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.locationWithCharacterIdImage.size) { index ->
                    val locations = uiState.locationWithCharacterIdImage[index]
                    LocationItem(locations, onCharacterClick)
                }
            }
        }
    }
}

@Composable
fun LocationItem(
    locationWithCharacterIdImage: LocationWithCharacterIdImage,
    onCharacterClick: (Int, List<Int>) -> Unit,
) {
    val titleTextStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White
    )
    val bodyTextStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color.White
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onCharacterClick(
                    locationWithCharacterIdImage.location.id,
                    locationWithCharacterIdImage.characters.map { it.id })
            }
            .clip(RoundedCornerShape(16.dp)) // Rounded corners
            .border(
                2.dp, Brush.verticalGradient(
                    listOf(
                        Color(139, 98, 190), Color(86, 98, 200), Color(114, 173, 219, 255)
                    )
                ), RoundedCornerShape(16.dp)
            )
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.DarkGray.copy(alpha = 0.7f), Color.Black.copy(alpha = 0.7f)
                    )
                )
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Name: ${locationWithCharacterIdImage.location.name}",
                    style = titleTextStyle
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Dimension: ${locationWithCharacterIdImage.location.dimension}",
                        style = bodyTextStyle
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    locationWithCharacterIdImage.characters.takeLast(6).forEach { character ->
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = character.image)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        transformations(CircleCropTransformation())
                                    }).build()
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}
