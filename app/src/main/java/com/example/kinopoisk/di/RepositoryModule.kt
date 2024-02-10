package com.example.kinopoisk.di

import com.example.kinopoisk.data.repository.FilmRepositoryImpl
import com.example.kinopoisk.domain.repository.FilmRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindFilmRepository(repo: FilmRepositoryImpl): FilmRepository
}
