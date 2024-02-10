package com.example.krendikova.data.api.model

import com.example.krendikova.domain.model.Film
import com.google.gson.annotations.SerializedName

class FilmDto(
    @SerializedName("filmId") val id: Int? = null,
    @SerializedName("nameRu") val name: String? = null,
    val genres: List<GenreDto>? = null,
    val year: Int? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
)

fun FilmDto.toDomain(): Film? {
    return Film(
        id = id?.toString() ?: return null,
        name = name ?: return null,
        genres = genres?.map { it.genre } ?: emptyList(),
        year = year,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
    )
}