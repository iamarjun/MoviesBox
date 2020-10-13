package com.arjun.moviesbox

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arjun.moviesbox.db.MovieDatabase
import com.arjun.moviesbox.model.Movie
import com.arjun.moviesbox.repository.MovieRemoteMediator
import kotlinx.coroutines.flow.Flow

class MovieViewModel @ViewModelInject constructor(
    private val database: MovieDatabase,
    private val restAPI: TmdbAPI
) : ViewModel() {

    val getPopularMovieList: Flow<PagingData<Movie>>
        get() = Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(
                restAPI,
                database
            ),
            pagingSourceFactory = { database.movieDao.getMovieList() }
        ).flow.cachedIn(viewModelScope)
}