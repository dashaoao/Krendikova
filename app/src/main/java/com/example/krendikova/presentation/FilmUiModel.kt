package com.example.krendikova.presentation

import com.example.krendikova.domain.model.Film

data class FilmUiModel(
    val id: String,
    val name: String,
    val genres: List<String>,
    val year: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
)

fun Film.toUi() = FilmUiModel(
    id = id,
    name = name,
    genres = genres,
    year = year,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,)