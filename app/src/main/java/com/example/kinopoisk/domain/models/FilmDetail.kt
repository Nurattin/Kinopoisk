package com.example.kinopoisk.domain.models

data class FilmDetail(
    val name: String,
    val description: String,
    val poster: Image,
    val genres: List<Genre>,
    val countries: List<Country>,
)
