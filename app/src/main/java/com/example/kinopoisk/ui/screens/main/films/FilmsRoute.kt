package com.example.kinopoisk.ui.screens.main.films

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kinopoisk.domain.models.Country
import com.example.kinopoisk.domain.models.Genre
import com.example.kinopoisk.utils.ObserveEffect

@Composable
fun FilmsRoute(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val viewModel: FilmsViewModel = hiltViewModel()
    val uiState by viewModel.filmsUiState.collectAsStateWithLifecycle()

    ObserveEffect(viewModel.effectFlow) { effect ->
        when (effect) {
            is FilmsEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    BackHandler(uiState.selectedFilm != null && configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        viewModel.obtainEvent(
            FilmsEvent.ClearSelectedFilm
        )
    }

    FilmsScreen(
        modifier = modifier,
        uiState = uiState,
        onEvent = viewModel::obtainEvent,
    )

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        uiState.selectedFilm?.let { selectedFilm ->
            FilmsDetailScreen(
                modifier = Modifier,
                poster = selectedFilm.poster.resource,
                descriptor = selectedFilm.description,
                name = selectedFilm.name,
                genre = selectedFilm.genres.map(Genre::name).joinToString(", "),
                country = selectedFilm.countries.map(Country::name).joinToString(", "),
                error = uiState.errorFilmDetail,
                loading = uiState.filmDetailLoading,
                onClickBack = {
                    viewModel.obtainEvent(
                        FilmsEvent.ClearSelectedFilm
                    )
                },
                onClickRetry = {
                    viewModel.obtainEvent(FilmsEvent.RefreshFilmDetail(selectedFilm.id))
                }
            )
        }
    }
}