package com.example.krendikova.di

import com.example.krendikova.domain.usecase.GetFavouriteFilmsUseCase
import com.example.krendikova.domain.usecase.GetFilmsUseCase
import com.example.krendikova.domain.usecase.GetPopularFilmsUseCase
import com.example.krendikova.domain.usecase.OnFavoriteClickUseCase
import com.example.krendikova.presentation.films.FilmsFragment
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    factory<GetFilmsUseCase>(named(FilmsFragment.Companion.Type.POPULAR.name)) {
        GetPopularFilmsUseCase(filmsRepository = get())
    }

    factory<GetFilmsUseCase>(named(FilmsFragment.Companion.Type.FAVOURITE.name)) {
        GetFavouriteFilmsUseCase(filmsRepository = get())
    }

    factory {
        OnFavoriteClickUseCase(filmsRepository = get())
    }
}