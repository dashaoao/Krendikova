package com.example.krendikova.presentation.films

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.krendikova.domain.repository.FilmsRepository
import com.example.krendikova.domain.usecase.GetPopularFilmsUseCase
import com.example.krendikova.domain.usecase.OnFavoriteClickUseCase
import com.example.krendikova.presentation.toUi
import com.example.krendikova.utils.runCatchingNonCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilmsViewModel(
    private val filmsRepository: FilmsRepository,
    private val getPopularFilmsUseCase: GetPopularFilmsUseCase,
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
                getPopularFilmsUseCase(keyword).map { it.toUi() }
            }
                .onSuccess { films ->
                    _uiState.update { it.copy(films = films, isLoading = false, isError = false) }
                }
                .onFailure {
                    _uiState.update { it.copy(isError = true) }
                }
        }
    }

    fun onFavoriteClick(idFilm: String){
        viewModelScope.launch {
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