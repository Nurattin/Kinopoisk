package com.example.kinopoisk.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberKinopoiskAppState(
    navController: NavHostController = rememberNavController(),
): KinopoiskAppState {
    return remember(navController) {
        KinopoiskAppState(
            navController = navController,
        )
    }
}

@Stable
class KinopoiskAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun onBackClick() {
        navController.popBackStack()
    }
}
