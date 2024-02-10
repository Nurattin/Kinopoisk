package com.example.kinopoisk.data.network_layer.models


import androidx.annotation.Keep
import com.example.kinopoisk.domain.models.Film
import com.example.kinopoisk.domain.models.Image
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FilmsWrapperDto(
    val films: List<FilmDto>,
    val pagesCount: Int
) {
    @Keep
    @Serializable
    data class FilmDto(
        val filmId: String,
        val genres: List<GenreDto>,
        val nameRu: String,
        val posterUrlPreview: String,
        val year: String,
    )
}

fun FilmsWrapperDto.FilmDto.toFilm(favorite: Boolean) = Film(
    id = filmId,
    genres = genres.map(GenreDto::toGenre),
    name = nameRu,
    poster = Image(
        resource = posterUrlPreview,
    ),
    year = year,
    favorite = favorite,
)
