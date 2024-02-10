package com.example.krendikova.presentation.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.krendikova.domain.repository.FilmsRepository
import com.example.krendikova.presentation.toUi
import com.example.krendikova.utils.runCatchingNonCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilmsViewModel(
    private val filmsRepository: FilmsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FilmsUiState())
    val uiState: StateFlow<FilmsUiState> = _uiState.asStateFlow()

    init {
        loadFilms()
    }

    fun loadFilms() {
        viewModelScope.launch {
            runCatchingNonCancellation {
                _uiState.update { it.copy(isLoading = true) }
                filmsRepository.getPopularFilms().map { it.toUi() }
            }
                .onSuccess { films ->
                    _uiState.update { it.copy(films = films, isLoading = false, isError = false) }
                }
                .onFailure {
                    _uiState.update { it.copy(isError = true) }
                }
        }
    }
}