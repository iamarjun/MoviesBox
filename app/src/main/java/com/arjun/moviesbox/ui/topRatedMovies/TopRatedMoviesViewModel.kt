package com.arjun.moviesbox.ui.topRatedMovies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.arjun.moviesbox.TmdbAPI
import com.arjun.moviesbox.model.Movie
import com.arjun.moviesbox.util.Resource

class TopRatedMoviesViewModel @ViewModelInject constructor(private val restApi: TmdbAPI) :
    ViewModel() {

    val topRatedMovies: LiveData<Resource<List<Movie>>>
        get() = liveData {
            emit(Resource.Loading)

            try {
                val result = restApi.getTopRatedMovies(1)
                emit(Resource.Success(result.movies))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
}