package com.example.kinopoisk.data.models

import androidx.annotation.Keep
import com.example.kinopoisk.domain.models.Country
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CountryDto(
    val country: String
)

fun CountryDto.toCountry() = Country(
    name = country,
)
