package com.arjun.moviesbox.util

import android.text.TextUtils
import com.arjun.moviesbox.model.Genre
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getGenres(genres: List<Genre>): String {
    val currentGenres = mutableListOf<String>()
    for (genre in genres) {
        currentGenres.add(genre.name)
    }
    return TextUtils.join(", ", currentGenres)
}

fun String.getReleaseDate(): String? {
    val input = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val output = SimpleDateFormat("yyyy", Locale.US)
    val newDate = try {
        input.parse(this)
    } catch (e: ParseException) {
        null
    }
    newDate?.let {
        return output.format(newDate)
    }
    return null
}

fun Int.getRuntime(): String {
    val hour = this / 60
    val minute = this % 60
    return String.format(Locale.US, "%dh %02d min", hour, minute)
}