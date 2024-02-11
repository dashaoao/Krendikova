package com.example.krendikova.di

import com.example.krendikova.domain.usecase.GetPopularFilmsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GetPopularFilmsUseCase(filmsRepository = get())
    }
}