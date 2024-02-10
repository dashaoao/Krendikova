package com.example.krendikova.di

import com.example.krendikova.data.api.AuthHeaderInterceptor
import com.example.krendikova.data.api.FilmsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"

val networkModule = module {

    single{
        val retrofit: Retrofit = get()
        retrofit.create(FilmsApi::class.java)
    }

    single <Retrofit> {
        val okHttpClient: OkHttpClient = get()
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        val httpLoggingInterceptor : HttpLoggingInterceptor = get()
        val authHeaderInterceptor : AuthHeaderInterceptor = get()
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authHeaderInterceptor)
            .build()
    }

    single {
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    factory {
        AuthHeaderInterceptor(token = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    }
}