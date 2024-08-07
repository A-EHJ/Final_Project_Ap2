package com.example.rickmorty.Screen.Character

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getStatusColor(status: String): Color {
    return when (status) {
        "Alive" -> Color.Green
        "unknown" -> Color.Yellow
        "Dead" -> Color.Red
        else -> Color.Gray
    }
}
