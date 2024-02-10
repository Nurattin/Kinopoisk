package com.example.kinopoisk.data.network_layer

import com.example.kinopoisk.data.network_layer.models.FilmDetailDto
import com.example.kinopoisk.data.network_layer.models.FilmsWrapperDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("/api/v2.2/films/top")
    suspend fun getFilms(
        @Query("type") type: String,
    ): FilmsWrapperDto

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmById(
        @Path("type") type: String,
    ): FilmDetailDto
}
