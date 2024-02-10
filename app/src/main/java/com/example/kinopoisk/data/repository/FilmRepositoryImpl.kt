package com.example.kinopoisk.data.repository

import com.example.kinopoisk.data.network_layer.KinopoiskApi
import com.example.kinopoisk.data.network_layer.models.toFilm
import com.example.kinopoisk.data.network_layer.models.toFilmDetail
import com.example.kinopoisk.data.store_layer.dao.FilmDao
import com.example.kinopoisk.data.store_layer.models.FilmEntity
import com.example.kinopoisk.data.store_layer.models.toFilm
import com.example.kinopoisk.di.IoDispatcher
import com.example.kinopoisk.domain.models.Film
import com.example.kinopoisk.domain.models.FilmDetail
import com.example.kinopoisk.domain.models.toFileEntity
import com.example.kinopoisk.domain.repository.FileType
import com.example.kinopoisk.domain.repository.FilmRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi,
    private val dao: FilmDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FilmRepository {
    override fun getFilms(type: FileType, force: Boolean): Flow<List<Film>> =
        flow {
            val filmsDto = api.getFilms(type.type)
            val film = filmsDto.films.map { filmDto ->
                filmDto.toFilm(false)
            }

            emit(film)
        }.flowOn(ioDispatcher)

    override fun getFavoriteFilms(): Flow<List<Film>> = dao.getAllFilmsFlow().map {
        it.map(FilmEntity::toFilm)
    }

    override fun getFilmDetail(id: String, force: Boolean): Flow<FilmDetail> = flow {
        val filmDetailDto = api.getFilmById(id)
        val filmDetail = filmDetailDto.toFilmDetail()

        emit(filmDetail)
    }.flowOn(ioDispatcher)

    override fun setFavorite(film: Film): Flow<Boolean> = flow {
        when (film.favorite) {
            true -> dao.deleteFilmById(film.id)
            false -> dao.insertFilm(film.toFileEntity(true))
        }
        emit(!film.favorite)
    }
}
