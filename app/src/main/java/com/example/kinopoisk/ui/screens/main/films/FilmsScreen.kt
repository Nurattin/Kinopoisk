package com.example.kinopoisk.ui.screens.main.films

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.models.Country
import com.example.kinopoisk.domain.models.Film
import com.example.kinopoisk.domain.models.FilmDetail
import com.example.kinopoisk.domain.models.Genre
import com.example.kinopoisk.ui.components.KinopoiskEmptyList
import com.example.kinopoisk.ui.components.KinopoiskErrorScreen
import com.example.kinopoisk.ui.components.KinopoiskLoadingScreen
import com.example.kinopoisk.ui.screens.main.films.components.FilmsBottomNavigation
import com.example.kinopoisk.ui.screens.main.films.components.FilmsList
import com.example.kinopoisk.ui.screens.main.films.components.KinopoiskSearchBar

@Preview(
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
private fun PreviewLandscapeFilmsScreen() {
    MaterialTheme {
        FilmsScreen(
            uiState = FilmsUiState(
                films = Film.generateMock(5, false),
                favoriteFilms = Film.generateMock(5, true),
                selectedFilm = FilmDetail.mock
            ),
            onEvent = {},
        )
    }
}

@Preview(
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
private fun PreviewPortraitFilmsScreen() {
    MaterialTheme {
        FilmsScreen(
            uiState = FilmsUiState(
                films = Film.generateMock(5, false),
                favoriteFilms = Film.generateMock(5, true),
            ),
            onEvent = {},
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FilmsScreen(
    modifier: Modifier = Modifier,
    uiState: FilmsUiState,
    onEvent: (FilmsEvent) -> Unit,
) {
    val configuration = LocalConfiguration.current

    var selectedFilmsType by rememberSaveable {
        mutableStateOf(FilmsType.Popular)
    }

    var searchBarIsActive by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1f else .5f),
            backgroundColor = MaterialTheme.colorScheme.background,
            topBar = {
                if (searchBarIsActive) {
                    KinopoiskSearchBar(
                        modifier = Modifier
                            .statusBarsPadding(),
                        value = uiState.searchBarText,
                        onValueChange = { newValue ->
                            onEvent(
                                FilmsEvent.ChangeSearchBarText(newValue)
                            )
                        },
                        onClickBack = {
                            searchBarIsActive = false
                        },
                    )
                } else {
                    TopAppBar(
                        windowInsets = WindowInsets.statusBars,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        backgroundColor = MaterialTheme.colorScheme.background,
                        elevation = 0.dp,
                        title = {
                            Crossfade(
                                targetState = selectedFilmsType,
                                label = "CrossfadeTitle"
                            ) { type ->
                                Text(
                                    text = stringResource(type.text),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }

                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    searchBarIsActive = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                    )
                }
            },
            bottomBar = {
                FilmsBottomNavigation(
                    selectedFilmsType = selectedFilmsType,
                    onClickType = { type ->
                        selectedFilmsType = type
                    }
                )
            }
        ) { innerPadding ->
            val films = when (selectedFilmsType) {
                FilmsType.Popular -> uiState.films
                FilmsType.Favorite -> uiState.favoriteFilms
            }
            if (!uiState.error.isNullOrBlank() && selectedFilmsType != FilmsType.Favorite) {
                KinopoiskErrorScreen(
                    errorText = uiState.error.toString(),
                    onClickRetry = {
                        onEvent(FilmsEvent.RefreshData)
                    }
                )
            } else if (
                (uiState.filmsLoading && selectedFilmsType == FilmsType.Popular)
                || (uiState.favoriteFilmsLoading && selectedFilmsType == FilmsType.Favorite)
            ) {
                KinopoiskLoadingScreen()
            } else if (!uiState.filmsLoading && !uiState.favoriteFilmsLoading && films.isEmpty()) {
                KinopoiskEmptyList()
            } else {
                FilmsList(
                    modifier = Modifier
                        .padding(innerPadding),
                    films = films,
                    onEvent = onEvent,
                )
            }
        }
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            uiState.selectedFilm?.let { selectedFilm ->
                FilmsDetailScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    poster = selectedFilm.poster.resource,
                    descriptor = selectedFilm.description,
                    name = selectedFilm.name,
                    genre = selectedFilm.genres.map(Genre::name).joinToString(", "),
                    country = selectedFilm.countries.map(Country::name).joinToString(", "),
                    error = uiState.errorFilmDetail,
                    loading = uiState.filmDetailLoading,
                    onClickBack = {
                        onEvent(FilmsEvent.ClearSelectedFilm)
                    },
                    onClickRetry = {
                        onEvent(FilmsEvent.RefreshFilmDetail(selectedFilm.id))
                    }
                )
            }
        }
    }
}

enum class FilmsType(@StringRes val text: Int) {
    Popular(R.string.popular),
    Favorite(R.string.favorites),
}