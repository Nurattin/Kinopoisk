package com.example.kinopoisk.domain.models

import com.example.kinopoisk.data.store_layer.models.FilmEntity
import java.util.UUID

data class Film(
    val id: String,
    val name: String,
    val poster: Image,
    val genres: List<Genre>,
    val year: String,
    val favorite: Boolean,
) {
    companion object {
        fun generateMock(
            size: Int = 1,
            favorite: Boolean = false,
        ): List<Film> {
            return List(size) {
                Film(
                    id = UUID.randomUUID().toString(),
                    name = "Interstellar",
                    poster = Image("https://example.com/poster/interstellar"),
                    genres = listOf(Genre("Sci-Fi"), Genre("Adventure")),
                    year = "2014",
                    favorite = favorite
                )
            }
        }
    }
}


fun Film.toFileEntity(favorite: Boolean) = FilmEntity(
    filmId = id,
    nameRu = name,
    posterUrlPreview = poster.resource,
    favorite = favorite,
    year = year,
    genre = genres,
)