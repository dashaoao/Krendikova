package com.example.krendikova.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.krendikova.data.database.model.FavouriteFilmDbModel

@Database(
    entities = [
        FavouriteFilmDbModel::class
    ],
    version = 1
)
abstract class FilmsDatabase : RoomDatabase() {
    abstract fun favouriteFilmsDao(): FavouriteFilmsDao

    companion object {
        private var instance: FilmsDatabase? = null
        private val lock = Any()
        private const val db_name = "films"

        fun getInstance(application: Application): FilmsDatabase {
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }
                val db = Room.databaseBuilder(application, FilmsDatabase::class.java, db_name)
                    .fallbackToDestructiveMigration()
                    .build()
                instance = db
                return db
            }
        }
    }
}