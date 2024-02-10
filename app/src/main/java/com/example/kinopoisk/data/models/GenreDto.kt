package com.example.kinopoisk.data.models

import androidx.annotation.Keep
import com.example.kinopoisk.domain.models.Genre
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GenreDto(
    val genre: String
)

fun GenreDto.toGenre() = Genre(
    name = genre,
)
