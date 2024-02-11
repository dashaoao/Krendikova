package com.example.krendikova.presentation.films

import com.example.krendikova.presentation.FilmUiModel

data class FilmsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val films: List<FilmUiModel> = emptyList(),
    val isSearch: Boolean = false,
    val isPlaceholder: Boolean = false
)