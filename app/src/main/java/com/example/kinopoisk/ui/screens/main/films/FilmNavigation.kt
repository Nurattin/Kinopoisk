package com.example.kinopoisk.ui.screens.main.films

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.kinopoisk.ui.screens.main.navigation.mainNavigationRoute


fun NavGraphBuilder.films(
    navController: NavController,
) {
    composable(
        route = mainNavigationRoute,
    ) {
        FilmsRoute(
            navController = navController,
        )
    }
}
