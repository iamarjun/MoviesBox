package com.arjun.moviesbox.db

import androidx.room.TypeConverter
import com.arjun.moviesbox.model.MovieType

class Converters {

    @TypeConverter
    fun toMovieType(value: String) = enumValueOf<MovieType>(value)

    @TypeConverter
    fun fromMovieType(value: MovieType) = value.name

}