package com.example.krendikova.data

import com.example.krendikova.data.api.FilmsApi
import com.example.krendikova.data.api.model.toDomain
import com.example.krendikova.data.database.FavouriteFilmsDao
import com.example.krendikova.data.database.model.toFilmDbModel
import com.example.krendikova.domain.model.Film
import com.example.krendikova.domain.repository.FilmsRepository

class FilmsRepositoryImpl(
    private val filmsApi: FilmsApi,
    private val favouriteFilmsDao: FavouriteFilmsDao
) : FilmsRepository {

    override suspend fun getPopularFilms(): List<Film> {
        val favoriteList = favouriteFilmsDao.getAllIds()
        return filmsApi.getTopFilms().films.mapNotNull { it.toDomain(
            favoriteList.contains(it.id.toString()) || favoriteList.contains(it.kinopoiskId.toString())
        ) }
    }

    override suspend fun getFilm(id: String): Film {
        val isFavorite = favouriteFilmsDao.getAllIds().contains(id)
        return filmsApi.getFilm(id).toDomain(isFavorite) ?: throw Exception("Film was not found")
    }

    override suspend fun searchFilm(keyword: String): List<Film> {
        val favoriteList = favouriteFilmsDao.getAllIds()
        return filmsApi.getFilmsByKeyword(keyword = keyword).items.mapNotNull {
            it.toDomain(
                favoriteList.contains(it.id.toString()) || favoriteList.contains(it.kinopoiskId.toString())
            )
        }
    }

    override suspend fun addToFavorites(filmId: String) {
        val result = filmsApi.getFilm(id = filmId)
        favouriteFilmsDao.saveFilm(
            result.toFilmDbModel() ?: throw IllegalArgumentException("Film was not found")
        )
    }

    override suspend fun deleteFromFavorites(filmId: String) {
        val result = favouriteFilmsDao.getFilm(filmId = filmId)
        favouriteFilmsDao.deleteFilm(
            result ?: throw IllegalArgumentException("Film was not found")
        )
    }

}