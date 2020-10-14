package com.arjun.moviesbox

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.arjun.moviesbox.db.MovieDatabase

class MovieViewModel @ViewModelInject constructor(
    private val database: MovieDatabase,
    private val restAPI: TmdbAPI
) : ViewModel()