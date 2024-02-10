package com.example.kinopoisk.ui.screens.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation

const val MAIN_ROUTE_PATTERN = "main_graph"
const val mainNavigationRoute = "main_route"

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
    this.navigate(MAIN_ROUTE_PATTERN, navOptions)
}

fun NavGraphBuilder.main(
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = MAIN_ROUTE_PATTERN,
        startDestination = mainNavigationRoute,
    ) {
        nestedGraphs()
    }
}