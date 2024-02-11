package com.example.kinopoisk.domain.models

data class FilmDetail(
    val id: String,
    val name: String,
    val description: String,
    val poster: Image,
    val genres: List<Genre>,
    val countries: List<Country>,
) {
    companion object {
        val mock = FilmDetail(
            id = "1",
            name = "Interstellar",
            description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
            poster = Image("https://example.com/poster/interstellar"),
            genres = listOf(Genre("Sci-Fi"), Genre("Adventure")),
            countries = listOf(Country("United States")),
        )
    }
}
