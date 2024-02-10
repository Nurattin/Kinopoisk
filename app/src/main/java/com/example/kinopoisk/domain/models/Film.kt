package com.example.kinopoisk.domain.models

data class Film(
    val id: Int,
    val name: String,
    val poster: Image,
    val genres: List<Genre>,
    val year: String,
    val favorite: Boolean,
)
