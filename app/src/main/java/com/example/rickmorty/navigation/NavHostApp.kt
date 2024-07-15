package com.example.rickmorty.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rickmorty.Screen.Rick.CharacterListScreen

@Composable
fun NavHostApp(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.CharacterList
    ) {
        composable<Screen.CharacterList> {
            CharacterListScreen(
                onCharacterClick = {

                },
                onNextPageClick = {

                },
                onPrevPageClick = {

                }
            )
        }
    }
}
