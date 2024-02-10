package com.example.kinopoisk.data.store_layer

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kinopoisk.data.store_layer.converter.GenresConverters
import com.example.kinopoisk.data.store_layer.dao.FilmDao
import com.example.kinopoisk.data.store_layer.models.FilmEntity

@Database(
    entities = [FilmEntity::class],
    version = 1
)
@TypeConverters(GenresConverters::class)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun getFilmDao(): FilmDao
}
