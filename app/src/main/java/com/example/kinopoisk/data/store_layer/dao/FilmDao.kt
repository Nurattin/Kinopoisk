package com.example.kinopoisk.data.store_layer.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinopoisk.data.store_layer.models.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: FilmEntity)

    @Query("DELETE FROM films WHERE filmId = :filmId")
    suspend fun deleteFilmById(filmId: String)

    @Query("SELECT * FROM films WHERE filmId = :filmId")
    suspend fun findFilmById(filmId: String): FilmEntity?

    @Query("SELECT * FROM films WHERE favorite == 1 AND LOWER(nameRu) LIKE '%' || LOWER(:keyWord) || '%'")
    fun getAllFilmsFlow(keyWord: String): Flow<List<FilmEntity>>
}