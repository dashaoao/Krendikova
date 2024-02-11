package com.example.krendikova.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.krendikova.data.database.model.FavouriteFilmDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteFilmsDao {

    @Query("SELECT * FROM favourite_films")
    fun getAll(): Flow<List<FavouriteFilmDbModel>>

    @Query("SELECT id FROM favourite_films")
    suspend fun getAllIds(): List<String>

    @Query("SELECT * FROM favourite_films WHERE id = :filmId")
    suspend fun getFilm(filmId: String): FavouriteFilmDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilm(favouriteFilmDbModel: FavouriteFilmDbModel)

    @Delete
    suspend fun deleteFilm(favouriteFilmDbModel: FavouriteFilmDbModel)

}