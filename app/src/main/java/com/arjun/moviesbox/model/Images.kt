package com.arjun.moviesbox.model


import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("backdrops")
    val backdrops: List<Image> = listOf(),
    @SerializedName("posters")
    val posters: List<Image> = listOf()
)