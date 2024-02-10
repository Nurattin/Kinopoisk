package com.example.kinopoisk.data.network_layer.models


import androidx.annotation.Keep
import com.example.kinopoisk.domain.models.FilmDetail
import com.example.kinopoisk.domain.models.Image
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FilmDetailDto(
    val filmId: String,
    val countries: List<CountryDto>,
    val description: String,
    val genres: List<GenreDto>,
    val nameRu: String,
    val posterUrl: String,
)

fun FilmDetailDto.toFilmDetail() = FilmDetail(
    id = filmId,
    countries = countries.map(CountryDto::toCountry),
    description = description,
    genres = genres.map(GenreDto::toGenre),
    name = nameRu,
    poster = Image(
        posterUrl,
    ),
)
