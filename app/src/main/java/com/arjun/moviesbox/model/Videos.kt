package com.arjun.moviesbox.model


import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results")
    val results: List<Video> = listOf()
)