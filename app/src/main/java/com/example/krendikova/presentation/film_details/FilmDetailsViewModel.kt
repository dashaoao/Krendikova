package com.example.krendikova.presentation.film_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.krendikova.domain.repository.FilmsRepository
import com.example.krendikova.utils.runCatchingNonCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilmDetailsViewModel(
    private val filmsRepository: FilmsRepository,
    private val filmDetailsId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilmDetailsUiState())
    val uiState: StateFlow<FilmDetailsUiState> = _uiState.asStateFlow()

    init {
        loadFilm()
    }

    fun loadFilm() {
        viewModelScope.launch {
            runCatchingNonCancellation {
                _uiState.update { it.copy(isLoading = true) }
                filmsRepository.getFilm(filmDetailsId)
            }
                .onSuccess { film ->
                    _uiState.update {
                        it.copy(
                            title = film.name,
                            descr = film.descr?: "",
                            genre = film.genres.joinToString(", "),
                            country = film.countries.joinToString(", "),
                            imgUrl = film.posterUrl ?: "",
                            isLoading = false,
                            isError = false
                        )
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(isError = true) }
                }
        }
    }
}