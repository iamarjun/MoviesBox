package com.arjun.moviesbox.db

import androidx.room.*
import com.arjun.moviesbox.model.Movie
import com.arjun.moviesbox.model.MovieType

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(list: List<Movie>)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Query("select * from movie where movieType = :movieType")
    fun getMovieList(movieType: MovieType): List<Movie>

    @Query("delete from movie")
    suspend fun clearMovie()

}