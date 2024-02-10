package com.example.kinopoisk.domain.models

data class Film(
    val name: String,
    val poster: Image,
    val genres: List<Genre>,
    val year: Int,
    val favorite: Boolean,
)
