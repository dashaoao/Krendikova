package com.example.krendikova.data.api

import com.example.krendikova.data.api.model.FilmDto
import com.example.krendikova.data.api.model.GetFilmsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmsApi {
    @GET("v2.2/films/top")
    suspend fun getTopFilms(): GetFilmsResponse

    @GET("v2.2/films/{id}")
    suspend fun getFilm(@Path("id") id: String): FilmDto

}