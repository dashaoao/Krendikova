package com.example.krendikova.data

import com.example.krendikova.data.api.FilmsApi
import com.example.krendikova.data.api.model.toDomain
import com.example.krendikova.domain.model.Film
import com.example.krendikova.domain.repository.FilmsRepository

class FilmsRepositoryImpl(
    private val filmsApi: FilmsApi,
    ) :FilmsRepository {

    override suspend fun getPopularFilms(): List<Film> {
        return filmsApi.getTopFilms().films.mapNotNull { it.toDomain() }
    }

    override suspend fun getFilm(id: String): Film {
        return filmsApi.getFilm(id).toDomain() ?: throw Exception("Film was not found")
    }

    override suspend fun searchFilm(keyword: String): List<Film> {
        return filmsApi.getFilmsByKeyword(keyword = keyword).items.mapNotNull { it.toDomain() }
    }

}