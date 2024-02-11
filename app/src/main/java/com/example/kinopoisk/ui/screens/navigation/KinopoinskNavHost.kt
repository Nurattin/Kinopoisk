package com.example.kinopoisk.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.kinopoisk.ui.screens.main.films.films
import com.example.kinopoisk.ui.screens.main.navigation.MAIN_ROUTE_PATTERN
import com.example.kinopoisk.ui.screens.main.navigation.main

@Composable
fun KinopoinskNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val startDestination = MAIN_ROUTE_PATTERN

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        main {
            films()
        }
    }
}
