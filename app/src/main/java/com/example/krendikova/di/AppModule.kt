package com.example.krendikova.di

import com.example.krendikova.presentation.film_details.FilmDetailsViewModel
import com.example.krendikova.presentation.films.FilmsFragment
import com.example.krendikova.presentation.films.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    viewModel { params ->
        val type: FilmsFragment.Companion.Type = params.get()
        FilmsViewModel(
            getFilmsUseCase = get(named(type.name)),
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