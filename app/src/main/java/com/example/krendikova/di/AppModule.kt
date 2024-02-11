package com.example.krendikova.di

import com.example.krendikova.domain.usecase.GetPopularFilmsUseCase
import com.example.krendikova.presentation.film_details.FilmDetailsViewModel
import com.example.krendikova.presentation.films.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        FilmsViewModel(
            filmsRepository = get(),
            getPopularFilmsUseCase = get(),
            onFavoriteClickUseCase = get()
        )
    }

    viewModel { params ->
        FilmDetailsViewModel(
            filmDetailsId = params.get(),
            filmsRepository = get()
        )
    }

}