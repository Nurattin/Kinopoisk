package com.example.kinopoisk.data

import com.example.kinopoisk.data.models.FilmDetailDto
import com.example.kinopoisk.data.models.FilmsWrapperDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("/api/v2.2/films/top")
    fun getFilms(
        @Query("type") type: String,
    ): FilmsWrapperDto

    @GET("/api/v2.2/films/{id}")
    fun getFilmById(
        @Path("type") type: String,
    ): FilmDetailDto
}
