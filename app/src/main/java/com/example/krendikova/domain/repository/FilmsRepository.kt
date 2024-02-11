package com.example.krendikova.domain.repository

import com.example.krendikova.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    suspend fun getPopularFilms(): List<Film>
    suspend fun getFilm(id: String): Film
    suspend fun searchFilm(keyword: String): List<Film>
    suspend fun addToFavorites(filmId: String)
    suspend fun deleteFromFavorites(filmId: String)
}