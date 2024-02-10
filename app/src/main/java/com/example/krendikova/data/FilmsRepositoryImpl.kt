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

//    override fun getPopularFilms(): Flow<DataState<List<Film>>> = flow {
//        emit(DataState.Loading())
//        val films = filmsApi.getTopFilms().mapToDataState { response ->
//            response.films.mapNotNull { it.toFilm() }
//        }
//        emit(films)
//    }
}