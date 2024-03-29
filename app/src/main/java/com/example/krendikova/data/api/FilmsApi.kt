package com.example.krendikova.data.api

import com.example.krendikova.data.api.model.FilmDto
import com.example.krendikova.data.api.model.GetFilmsResponse
import com.example.krendikova.data.api.model.SearchFilmsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsApi {
    @GET("v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopFilms(): GetFilmsResponse

    @GET("v2.2/films/{id}")
    suspend fun getFilm(@Path("id") id: String): FilmDto

    @GET("v2.2/films")
    suspend fun getFilmsByKeyword(
        @Query("keyword") keyword: String
    ): SearchFilmsResponse

}