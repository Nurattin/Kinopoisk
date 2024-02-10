package com.example.kinopoisk.data.repository

import com.example.kinopoisk.data.KinopoiskApi
import com.example.kinopoisk.data.models.toFilm
import com.example.kinopoisk.data.models.toFilmDetail
import com.example.kinopoisk.di.IoDispatcher
import com.example.kinopoisk.domain.models.Film
import com.example.kinopoisk.domain.models.FilmDetail
import com.example.kinopoisk.domain.repository.FileType
import com.example.kinopoisk.domain.repository.FilmRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FilmRepository {
    override fun getFilms(type: FileType, force: Boolean, onlyFavorite: Boolean): Flow<List<Film>> =
        flow {
            val filmsDto = api.getFilms(type.type)
            val film = filmsDto.films.map { filmDto ->
                filmDto.toFilm(false)
            }

            emit(film)
        }.flowOn(ioDispatcher)

    override fun getFilmDetail(id: String, force: Boolean): Flow<FilmDetail> = flow {
        val filmDetailDto = api.getFilmById(id)
        val filmDetail = filmDetailDto.toFilmDetail()

        emit(filmDetail)
    }.flowOn(ioDispatcher)

    override fun setFavorite(favorite: Boolean): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}
