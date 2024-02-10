package com.example.krendikova.domain.repository

import com.example.krendikova.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    suspend fun getPopularFilms(): List<Film>
}