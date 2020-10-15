package com.arjun.moviesbox.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.arjun.moviesbox.TmdbAPI
import com.arjun.moviesbox.model.MovieDetail
import com.arjun.moviesbox.util.Resource

class MovieDetailViewModel @ViewModelInject constructor(private val restApi: TmdbAPI) :
    ViewModel() {

    private val _movieId by lazy { MutableLiveData<Int>() }

    fun getMovieDetail(movieId: Int) {
        _movieId.value = movieId
    }

    val movieDetail: LiveData<Resource<MovieDetail>>
        get() = _movieId.switchMap {
            liveData {
                emit(Resource.Loading)
                try {
                    val result = restApi.getMovieDetails(it)
                    emit(Resource.Success(result))
                } catch (e: Exception) {
                    emit(Resource.Error(e))
                }
            }
        }
}