package com.example.krendikova.domain.model

data class Film(
    val id: String,
    val name: String,
    val descr: String?,
    val countries: List<String>,
    val genres: List<String>,
    val year: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val isFavorite: Boolean,
)
