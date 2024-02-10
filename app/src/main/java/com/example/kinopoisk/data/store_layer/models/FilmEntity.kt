package com.example.kinopoisk.data.store_layer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinopoisk.domain.models.Film
import com.example.kinopoisk.domain.models.Genre
import com.example.kinopoisk.domain.models.Image


@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey val filmId: String,
    val nameRu: String,
    val posterUrlPreview: String,
    val favorite: Boolean,
    val genre: List<Genre>,
    val year: String,
)

fun FilmEntity.toFilm() = Film(
    id = filmId,
    name = nameRu,
    poster = Image(
        posterUrlPreview
    ),
    favorite = favorite,
    year = year,
    genres = genre
)
