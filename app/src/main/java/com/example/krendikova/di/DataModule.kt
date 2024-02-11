package com.example.krendikova.di

import com.example.krendikova.data.FilmsRepositoryImpl
import com.example.krendikova.data.database.FilmsDatabase
import com.example.krendikova.domain.repository.FilmsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single {
        val db: FilmsDatabase = get()
        db.favouriteFilmsDao()
    }

    single {
        FilmsDatabase.getInstance(application = get()).favouriteFilmsDao()
    }

    single <FilmsRepository> {
        FilmsRepositoryImpl(filmsApi = get(), favouriteFilmsDao = get())
    }
}