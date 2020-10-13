package com.arjun.moviesbox.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arjun.moviesbox.model.Movie
import com.arjun.moviesbox.model.RemoteKeys
import com.arjun.tmdb.db.RemoteKeysDao

@Database(
    entities = [Movie::class, RemoteKeys::class],
    exportSchema = false,
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    abstract val remoteKeysDao: RemoteKeysDao

    companion object {
        private const val DB_NAME = "movie_db"
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: synchronized(this) {
                getMovieDatabase(context).also { INSTANCE = it }
            }
        }

        private fun getMovieDatabase(context: Context): MovieDatabase =
            Room.databaseBuilder(context, MovieDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}