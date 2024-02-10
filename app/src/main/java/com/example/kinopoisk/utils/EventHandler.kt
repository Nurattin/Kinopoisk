package com.example.kinopoisk.utils

interface EventHandler<T> {
    fun obtainEvent(event: T)
}