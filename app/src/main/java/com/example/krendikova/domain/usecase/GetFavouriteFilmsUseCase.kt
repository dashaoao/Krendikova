package com.example.krendikova.domain.usecase

import com.example.krendikova.domain.model.Film
import com.example.krendikova.domain.repository.FilmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavouriteFilmsUseCase(
    private val filmsRepository: FilmsRepository,
) : GetFilmsUseCase {
    override suspend fun invoke(keyword: String): Flow<List<Film>> =
        filmsRepository.getFavouriteFilms().map { list ->
            list.filter {
                it.name.lowercase().contains(keyword.lowercase())
            }
        }
}