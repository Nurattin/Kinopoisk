package com.example.kinopoisk.data.store_layer.converter

import androidx.room.TypeConverter
import com.example.kinopoisk.domain.models.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenresConverters {
    @TypeConverter
    fun fromGenreList(genres: List<Genre>): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenreList(genres: String): List<Genre> {
        val listType = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(genres, listType)
    }
}