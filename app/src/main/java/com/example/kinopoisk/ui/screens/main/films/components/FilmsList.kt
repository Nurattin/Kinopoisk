package com.example.kinopoisk.ui.screens.main.films.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kinopoisk.domain.models.Film
import com.example.kinopoisk.domain.models.Genre
import com.example.kinopoisk.ui.screens.main.films.FilmsEvent

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun FilmsList(
    modifier: Modifier = Modifier,
    films: List<Film>,
    onEvent: (FilmsEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(13.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(15.dp),
    ) {
        items(
            items = films,
            key = Film::id,
            contentType = {
                "film"
            }
        ) { film ->
            FilmCard(
                modifier = Modifier
                    .animateItemPlacement(),
                imageUrl = film.poster.resource,
                name = film.name,
                genre = film.genres.map(Genre::name).joinToString(", "),
                year = film.year,
                isFavorite = film.favorite,
                onClick = {
                    onEvent(FilmsEvent.SelectedFilm(film.id))
                },
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
