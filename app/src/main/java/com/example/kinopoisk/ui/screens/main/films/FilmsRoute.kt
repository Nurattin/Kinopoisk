package com.example.kinopoisk.ui.screens.main.films

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun FilmsRoute(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    val viewModel: FilmsViewModel = hiltViewModel()
    val uiState by viewModel.filmsUiState.collectAsStateWithLifecycle()

    FilmsScreen(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::obtainEvent,
    )
}