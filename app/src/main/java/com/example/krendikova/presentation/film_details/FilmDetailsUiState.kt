package com.example.krendikova.presentation.film_details

data class FilmDetailsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val title: String = "",
    val descr: String = "",
    val genre: String = "",
    val country: String = "",
    val imgUrl: String = ""
)