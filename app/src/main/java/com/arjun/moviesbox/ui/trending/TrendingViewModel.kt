package com.arjun.moviesbox.ui.trending

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.arjun.moviesbox.TmdbAPI
import com.arjun.moviesbox.model.Movie
import com.arjun.moviesbox.util.Resource

class TrendingViewModel @ViewModelInject constructor(private val restApi: TmdbAPI) : ViewModel() {
    val trending: LiveData<Resource<List<Movie>>>
        get() = liveData {
            emit(Resource.Loading)

            try {
                val result = restApi.getTrending("all", "week")
                emit(Resource.Success(result.movies))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
}