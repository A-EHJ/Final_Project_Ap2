package com.example.rickmorty.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rickmorty.Screen.Character.CharacterListScreen
import com.example.rickmorty.Screen.Character.CharacterScreen
import com.example.rickmorty.Screen.Location.LocationListScreen

@Composable
fun NavHostApp(navHostController: NavHostController, drawerState: DrawerState) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.CharacterList
    ) {
        composable<Screen.CharacterList> {
            CharacterListScreen(
                onCharacterClick = { navHostController.navigate(Screen.CharacterBody(it)) },
                drawer = drawerState
            )
        }

        composable<Screen.CharacterBody> {
            val args = it.toRoute<Screen.CharacterBody>()
            CharacterScreen(
                characterId = args.characterId,
                onBack = { navHostController.popBackStack() }
            )
        }

        composable<Screen.LocationList> {
            LocationListScreen(
                onLocationClick = { },
                drawer = drawerState
            )
        }
    }
}
