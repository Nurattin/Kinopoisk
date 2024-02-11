package com.example.kinopoisk.data.network_layer.models


import androidx.annotation.Keep
import com.example.kinopoisk.domain.models.FilmDetail
import com.example.kinopoisk.domain.models.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FilmDetailDto(
    @SerialName("kinopoiskId")
    val kinopoiskId: String,
    @SerialName("countries")
    val countries: List<CountryDto>,
    @SerialName("description")
    val description: String,
    @SerialName("genres")
    val genres: List<GenreDto>,
    @SerialName("nameRu")
    val nameRu: String,
    @SerialName("posterUrl")
    val posterUrl: String,
)

fun FilmDetailDto.toFilmDetail() = FilmDetail(
    id = kinopoiskId,
    countries = countries.map(CountryDto::toCountry),
    description = description,
    genres = genres.map(GenreDto::toGenre),
    name = nameRu,
    poster = Image(
        posterUrl,
    ),
)
