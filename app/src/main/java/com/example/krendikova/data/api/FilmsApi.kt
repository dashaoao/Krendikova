package com.example.krendikova.data.api

import com.example.krendikova.data.api.model.GetFilmsResponse
import retrofit2.http.GET

interface FilmsApi {
    @GET("v2.2/films/top")
    suspend fun getTopFilms(): GetFilmsResponse
}