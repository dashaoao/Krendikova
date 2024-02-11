package com.example.krendikova.domain.usecase

import com.example.krendikova.domain.repository.FilmsRepository
import kotlinx.coroutines.flow.flowOf

class GetPopularFilmsUseCase(
    private val filmsRepository: FilmsRepository,
) : GetFilmsUseCase {
    override suspend operator fun invoke(keyword: String) = flowOf(
        if (keyword.isNotBlank())
            filmsRepository.searchFilm(keyword = keyword)
        else
            filmsRepository.getPopularFilms()
    )
}