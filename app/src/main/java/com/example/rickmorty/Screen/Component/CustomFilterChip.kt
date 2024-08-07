package com.example.rickmorty.Screen.Component

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults.filterChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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
