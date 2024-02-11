package com.example.krendikova.domain.usecase

import com.example.krendikova.domain.repository.FilmsRepository

class OnFavoriteClickUseCase(private val filmsRepository: FilmsRepository) {
    suspend operator fun invoke(idFilm: String){
        val isFavorite = filmsRepository.getFilm(idFilm).isFavorite
        if (isFavorite){
            filmsRepository.deleteFromFavorites(idFilm)
        } else {
            filmsRepository.addToFavorites(idFilm)
        }
    }
}