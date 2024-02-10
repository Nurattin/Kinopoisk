package com.example.kinopoisk.ui.screens.main.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.data.collectAsResult
import com.example.kinopoisk.domain.models.Film
import com.example.kinopoisk.domain.repository.FileType
import com.example.kinopoisk.domain.repository.FilmRepository
import com.example.kinopoisk.utils.EventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_TIME_DELAY = 500L

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val filmRepository: FilmRepository,
) : ViewModel(), EventHandler<FilmsEvent> {

    private val _filmsUiState = MutableStateFlow(FilmsUiState())
    val filmsUiState = _filmsUiState.asStateFlow()

    private var favoriteFilmsJob: Job? = null
    private var searchJob: Job? = null

    init {
        getFilms()
        observeFavoriteFilms()
    }

    override fun obtainEvent(event: FilmsEvent) {
        when (event) {
            is FilmsEvent.ChangeFavorite -> {
                changeFavorite(event.film)
            }

            FilmsEvent.RefreshData -> {
                getFilms()
            }

            is FilmsEvent.ChangeSearchBarText -> {
                changeSearchBarText(event.value)
            }
        }
    }

    private fun changeSearchBarText(value: String) {
        _filmsUiState.update { currentState ->
            currentState.copy(
                searchBarText = value,
            )
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_TIME_DELAY)
            observeFavoriteFilms(value)
            getFilms(value)
        }
    }

    private fun changeFavorite(film: Film) {
        viewModelScope.launch {
            filmRepository.setFavorite(film).collectAsResult(
                onSuccess = { newValue ->
                    _filmsUiState.update { currentState ->
                        currentState.copy(
                            films = currentState.films.toMutableList()
                                .map { if (it.id == film.id) it.copy(favorite = newValue) else it },
                        )
                    }
                }
            )
        }
    }

    private fun observeFavoriteFilms(keyWord: String = "") {
        favoriteFilmsJob?.cancel()
        favoriteFilmsJob = viewModelScope.launch {
            filmRepository.observeFavoriteFilms(keyWord).collectAsResult(
                onSuccess = { films ->
                    _filmsUiState.update { currentState ->
                        currentState.copy(
                            favoriteFilms = films,
                            favoriteFilmsLoading = false,
                        )
                    }
                },
                onLoading = {
                    _filmsUiState.update { currentState ->
                        currentState.copy(
                            favoriteFilmsLoading = true,
                        )
                    }
                },
                onError = { ex, message ->
                    _filmsUiState.update { currentState ->
                        currentState.copy(
                            favoriteFilmsLoading = false,
                        )
                    }
                }
            )
        }
    }

    private fun getFilms(
        keyWord: String = "",
    ) {
        viewModelScope.launch {
            filmRepository.getFilms(
                type = FileType.TOP_100,
                keyWord = keyWord,
            ).collectAsResult(
                onSuccess = { films ->
                    _filmsUiState.update { currentState ->
                        currentState.copy(
                            films = films,
                            filmsLoading = false,
                            error = null,
                        )
                    }
                },
                onError = { ex, message ->
                    _filmsUiState.update { currentState ->
                        currentState.copy(
                            filmsLoading = false,
                            error = message,
                        )
                    }
                },
                onLoading = {
                    _filmsUiState.update { currentState ->
                        currentState.copy(
                            filmsLoading = true,
                            error = null,
                        )
                    }
                }
            )
        }
    }
}

data class FilmsUiState(
    val filmsLoading: Boolean = false,
    val favoriteFilmsLoading: Boolean = false,
    val error: String? = null,
    val films: List<Film> = emptyList(),
    val favoriteFilms: List<Film> = emptyList(),
    val searchBarText: String = "",
)

sealed interface FilmsEvent {
    data object RefreshData : FilmsEvent

    data class ChangeFavorite(val film: Film) : FilmsEvent

    data class ChangeSearchBarText(val value: String) : FilmsEvent
}