package com.example.kinopoisk.domain.repository

import com.example.kinopoisk.domain.models.Film
import com.example.kinopoisk.domain.models.FilmDetail
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    fun getFilms(
        type: FileType,
        force: Boolean,
    ): Flow<List<Film>>

    fun getFavoriteFilms(): Flow<List<Film>>

    fun getFilmDetail(
        id: String,
        force: Boolean,
    ): Flow<FilmDetail>

    fun setFavorite(
        film: Film,
    ): Flow<Boolean>
}

enum class FileType(val type: String) {
    TOP_100("TOP_100_POPULAR_FILMS")
}
