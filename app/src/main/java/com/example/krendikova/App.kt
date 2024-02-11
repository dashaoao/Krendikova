package com.example.krendikova

import android.app.Application
import com.example.krendikova.di.appModule
import com.example.krendikova.di.dataModule
import com.example.krendikova.di.domainModule
import com.example.krendikova.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(appModule, dataModule, networkModule, domainModule)
        }
    }
}