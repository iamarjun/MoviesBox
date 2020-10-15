package com.arjun.moviesbox.model

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String
)