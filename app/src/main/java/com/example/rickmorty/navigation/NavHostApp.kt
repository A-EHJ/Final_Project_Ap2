package com.example.rickmorty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rickmorty.Screen.Rick.CharacterListScreen
import com.example.rickmorty.Screen.Rick.CharacterScreen

@Composable
fun NavHostApp(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.CharacterList
    ) {
        composable<Screen.CharacterList> {
            CharacterListScreen(
                onCharacterClick = {navHostController.navigate(Screen.CharacterBody(it))}
            )
        }

        composable<Screen.CharacterBody> {
            val args = it.toRoute<Screen.CharacterBody>()
            CharacterScreen(
                characterId = args.characterId,
                onBack = { navHostController.popBackStack() }
            )
        }
    }
}
