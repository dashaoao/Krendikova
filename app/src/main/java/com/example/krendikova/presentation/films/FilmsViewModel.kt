package com.example.krendikova.presentation.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.krendikova.domain.usecase.GetFilmsUseCase
import com.example.krendikova.domain.usecase.OnFavoriteClickUseCase
import com.example.krendikova.presentation.toUi
import com.example.krendikova.utils.runCatchingNonCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilmsViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val onFavoriteClickUseCase: OnFavoriteClickUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(FilmsUiState())
    val uiState: StateFlow<FilmsUiState> = _uiState.asStateFlow()

    init {
        loadFilms()
    }

    fun loadFilms(keyword: String = "") {
        viewModelScope.launch {
            runCatchingNonCancellation {
                _uiState.update { it.copy(isLoading = true) }
                getFilmsUseCase(keyword)
            }
                .onSuccess { filmsFlow ->
                    filmsFlow.collect { films ->
                        _uiState.update { state ->
                            state.copy(
                                films = films.map { it.toUi() },
                                isLoading = false,
                                isError = false,
                                isPlaceholder = films.isEmpty()
                            )
                        }
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(isError = true) }
                }
        }
    }

    fun onFavoriteClick(idFilm: String) {
        viewModelScope.launch {
            runCatchingNonCancellation {
                _uiState.update { state ->
                    state.copy(
                        films = state.films.map {
                            if (it.id == idFilm) it.copy(isFavorite = !it.isFavorite)
                            else it
                        }
                    )
                }
                onFavoriteClickUseCase.invoke(idFilm)
            }
        }
    }
}