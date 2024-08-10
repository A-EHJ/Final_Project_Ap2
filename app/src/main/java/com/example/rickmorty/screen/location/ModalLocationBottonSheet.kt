package com.example.rickmorty.screen.location

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ModalLocationBottonSheet(
    unShowBottomSheet: () -> Unit,
    onGetCharacterLimitedFiltered: () -> Unit,
    onTextChange: (String) -> Unit,
    onResetFilters: () -> Unit,
    sheetState: SheetState,
    uiState: LocationUIState,
    scope: CoroutineScope,
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
                Icon(Icons.Default.Close, contentDescription = "Back")
            }
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        unShowBottomSheet()
                        onGetCharacterLimitedFiltered()
                    }
                }
            }) {
                Text("Filter")
                Icon(Icons.Default.Search, contentDescription = "Back")
            }
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        unShowBottomSheet()
                        onResetFilters()
                        onGetCharacterLimitedFiltered()
                    }
                }
            }) {
                Text("Reset")
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
