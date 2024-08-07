package com.example.rickmorty.Screen.Location

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.rickmorty.domain.models.LocationWithCharacterIdUrl
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
fun LocationListScreen(
    viewModel: LocationListViewModel = hiltViewModel(),
    onLocationClick: (Int) -> Unit,
    drawer: DrawerState,
    onPreviousPageClick: () -> Unit = { viewModel.getCharacterLimited(false) },
    onNextPageClick: () -> Unit = { viewModel.getCharacterLimited(true) },
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
        CharacterListBody(innerPadding, uiState, onLocationClick)
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {

                TextField(
                    value = uiState.text,
                    onValueChange = { },
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
                                            showBottomSheet = false
                                        }
                                    }
                                //onGetCharacterLimitedFiltered()
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
                                showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Cancel")
                        Icon(Icons.Default.Close, contentDescription = "Back")
                    }
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }) {
                        Text("Filter")
                        Icon(Icons.Default.Search, contentDescription = "Back")
                    }
                    Button(onClick = {
                        //resetFilters()
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                        //onGetCharacterLimitedFiltered()
                    }) {
                        Text("Reset")
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
                }
            }
        }
    }
}


@Composable
private fun CharacterListBody(
    innerPadding: PaddingValues,
    uiState: LocationUIState,
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
        } else if (uiState.errorMessage.isNotEmpty() || uiState.locationWithCharacterIdUrl.isEmpty()) {
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
                items(uiState.locationWithCharacterIdUrl.size) { index ->
                    val locations = uiState.locationWithCharacterIdUrl[index]
                    LocationItem(locations, onCharacterClick)
                }
            }
        }
    }
}


@Composable
fun LocationItem(
    locationWithCharacterIdUrl: LocationWithCharacterIdUrl,
    onCharacterClick: (Int) -> Unit,
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
            .clickable { onCharacterClick(locationWithCharacterIdUrl.location.id) }
            .clip(RoundedCornerShape(16.dp)) // Rounded corners
            .border(
                2.dp, Brush.verticalGradient(
                    listOf(
                        Color(139, 98, 190), Color(86, 98, 200), Color(114, 173, 219, 255)
                    )
                ), RoundedCornerShape(16.dp)
            ) // Border based on status
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
                    text = "Name: ${locationWithCharacterIdUrl.location.name}",
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
                        text = "Dimension: ${locationWithCharacterIdUrl.location.dimension}",
                        style = bodyTextStyle
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    locationWithCharacterIdUrl.characters.takeLast(6).forEach { character ->
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = character.url)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        transformations(CircleCropTransformation())
                                    }).build()
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp) // Aumenta el tamaño de la imagen
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}
