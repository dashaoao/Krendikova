package com.example.krendikova.domain.model

data class Film(
    val id: String,
    val name: String,
    val genres: List<String>,
    val year: Int?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
)
