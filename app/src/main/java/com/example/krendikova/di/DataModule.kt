package com.example.krendikova.di

import com.example.krendikova.data.FilmsRepositoryImpl
import com.example.krendikova.domain.repository.FilmsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

//    single {
//        AppDatabase.getInstance(application = androidApplication())
//    }
//
//    single {
//        val db: AppDatabase = get()
//        db.crewDao()
//    }

    single <FilmsRepository> {
        FilmsRepositoryImpl(filmsApi = get())
    }
}