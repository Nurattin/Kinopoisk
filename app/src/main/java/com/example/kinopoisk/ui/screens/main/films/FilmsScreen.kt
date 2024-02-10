package com.example.kinopoisk.ui.screens.main.films

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.models.Film
import com.example.kinopoisk.domain.models.Genre
import com.example.kinopoisk.ui.components.KinopoiskEmptyList
import com.example.kinopoisk.ui.components.KinopoiskErrorScreen
import com.example.kinopoisk.ui.components.KinopoiskLoadingScreen
import com.example.kinopoisk.ui.screens.main.films.components.FilmCard

@Preview
@Composable
private fun PreviewFilmsScreen() {
    MaterialTheme {
        FilmsScreen(
            uiState = FilmsUiState(),
            onEvent = {},
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FilmsScreen(
    modifier: Modifier = Modifier,
    uiState: FilmsUiState,
    onEvent: (FilmsEvent) -> Unit,
) {

    var selectedFilmsType by rememberSaveable {
        mutableStateOf(FilmsType.Popular)
    }

    var searchBarIsActive by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (searchBarIsActive) {
                TextField(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth(),
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                searchBarIsActive = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    },
                    trailingIcon = {
                        if (uiState.searchBarText.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    onEvent(FilmsEvent.ChangeSearchBarText(""))
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    value = uiState.searchBarText,
                    onValueChange = { newValue ->
                        onEvent(FilmsEvent.ChangeSearchBarText(newValue))
                    },
                    colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        placeholderColor = Color.Gray,
                        textColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.search),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                        )
                    },
                    singleLine = true,
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
                        IconButton(onClick = { searchBarIsActive = true }) {
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
            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    FilmsType.entries.forEach { type ->

                        val updateTransition = updateTransition(
                            targetState = type == selectedFilmsType,
                            label = "animateBottomNavigation",
                        )
                        val containerColor by updateTransition.animateColor(
                            label = "animateContainerColor",
                        ) {
                            when (it) {
                                true -> MaterialTheme.colorScheme.primary
                                false -> MaterialTheme.colorScheme.primary.copy(alpha = .5f)
                            }
                        }
                        val contentColor by updateTransition.animateColor(
                            label = "animateContentColor",
                        ) {
                            when (it) {
                                true -> MaterialTheme.colorScheme.background
                                false -> MaterialTheme.colorScheme.primary
                            }
                        }

                        Button(
                            onClick = {
                                selectedFilmsType = type
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = containerColor,
                                contentColor = contentColor
                            ),
                            contentPadding = PaddingValues(
                                vertical = 16.dp,
                                horizontal = 32.dp,
                            )
                        ) {
                            Text(
                                text = stringResource(id = type.text),
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            }
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
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(13.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(15.dp),
            ) {
                items(
                    items = films,
                    key = Film::id
                ) { film ->
                    FilmCard(
                        modifier = Modifier
                            .animateItemPlacement(),
                        imageUrl = film.poster.resource,
                        name = film.name,
                        genre = film.genres.map(Genre::name).joinToString(", "),
                        year = film.year,
                        isFavorite = film.favorite,
                        onClick = { /*TODO*/ },
                        onLongClick = {
                            onEvent(FilmsEvent.ChangeFavorite(film))
                        },
                        onClickFavorite = {
                            onEvent(FilmsEvent.ChangeFavorite(film))
                        }
                    )
                }
            }
        }
    }
}

enum class FilmsType(@StringRes val text: Int) {
    Popular(R.string.popular),
    Favorite(R.string.favorites),
}