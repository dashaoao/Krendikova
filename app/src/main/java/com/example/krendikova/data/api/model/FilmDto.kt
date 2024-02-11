package com.example.krendikova.data.api.model

import com.example.krendikova.domain.model.Film
import com.google.gson.annotations.SerializedName

class FilmDto(
    @SerializedName("kinopoiskId") val kinopoiskId: Int? = null,
    @SerializedName("filmId") val id: Int? = null,
    @SerializedName("nameRu") val name: String? = null,
    @SerializedName("description") val descr: String? = null,
    val genres: List<GenreDto>? = null,
    val countries: List<CountryDto>? = null,
    val year: Int? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
)

fun FilmDto.toDomain(
    isFavorite: Boolean
): Film? {
    return Film(
        id = id?.toString() ?: kinopoiskId?.toString() ?: return null,
        name = name ?: return null,
        descr = descr,
        genres = genres?.map { it.genre } ?: emptyList(),
        countries = countries?.map { it.country } ?: emptyList(),
        year = year,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        isFavorite = isFavorite
    )
}