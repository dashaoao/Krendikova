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

//    fun searchMoviesByWord(word: String) {
//        viewModelScope.launch {
//            try {
//                _loadingState.postValue(true)
//                val list = searchMoviesUseCase.execute(word)
//                if (list?.isEmpty() == true)
//                    _error.postValue(LoadState.NONE)
//                else
//                    _error.postValue(LoadState.SUCCESS)
//                _movies.postValue(list)
//            } catch (e: Exception) {
//                _movies.postValue(null)
//                _error.postValue(LoadState.NONE)
//            } finally {
//                _loadingState.postValue(false)
//            }
//        }
//    }
}