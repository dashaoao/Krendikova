package com.example.krendikova.domain.usecase

import com.example.krendikova.domain.repository.FilmsRepository


class GetPopularFilmsUseCase(
    private val filmsRepository: FilmsRepository,
) {
    suspend operator fun invoke(keyword: String) =
        if (keyword.isNotBlank())
            filmsRepository.searchFilm(keyword = keyword)
        else
            filmsRepository.getPopularFilms()
}