package com.example.krendikova.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.krendikova.data.api.model.FilmDto
import com.example.krendikova.data.database.Converters
import com.example.krendikova.domain.model.Film

@Entity(tableName = "favourite_films")
@TypeConverters(Converters::class)
data class FavouriteFilmDbModel(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String,
    val year: Int?,
    val descr: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val countries: List<String>,
    val genres: List<String>,
)

fun FilmDto.toFilmDbModel(): FavouriteFilmDbModel? {
    return FavouriteFilmDbModel(
        id = id?.toString() ?: kinopoiskId?.toString() ?: return null,
        name = name ?: return null,
        year = year,
        descr = descr,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        genres = genres?.map { it.genre } ?: emptyList(),
        countries = countries?.map { it.country } ?: emptyList(),
    )
}

fun FavouriteFilmDbModel.toDomain() = Film(
    id = id,
    name = name,
    posterUrl = posterUrl,
    posterUrlPreview = posterUrlPreview,
    descr = descr,
    year = year,
    countries = countries,
    genres = genres,
    isFavorite = true,
)