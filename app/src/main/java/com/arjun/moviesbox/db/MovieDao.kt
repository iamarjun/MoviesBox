package com.arjun.moviesbox.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.arjun.moviesbox.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(list: List<Movie>)

    @Update
    suspend fun updateMovie(recipe: Movie)

    @Query("select * from movie where id like '%'||:movieId||'%'")
    suspend fun getMovie(movieId: String): Movie

    @Query("select * from movie where id like '%'||:movieId||'%'")
    fun getMovieLiveData(movieId: String): LiveData<Movie>

    @Query("select * from movie order by popularity desc")
    fun getMovieList(): PagingSource<Int, Movie>

    @Query("select count(*) from movie where lower(title) like '%'||:query||'%'")
    suspend fun getCount(query: String): Int

    @Query("DELETE FROM movie")
    suspend fun clearMovie()

}