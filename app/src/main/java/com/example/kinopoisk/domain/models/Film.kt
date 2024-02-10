package com.example.kinopoisk.domain.models

import com.example.kinopoisk.data.store_layer.models.FilmEntity

data class Film(
    val id: String,
    val name: String,
    val poster: Image,
    val genres: List<Genre>,
    val year: String,
    val favorite: Boolean,
)

fun Film.toFileEntity(favorite: Boolean) = FilmEntity(
    filmId = id,
    nameRu = name,
    posterUrlPreview = poster.resource,
    favorite = favorite,
    year = year,
    genre = genres,
)