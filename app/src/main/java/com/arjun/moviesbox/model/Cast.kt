package com.arjun.moviesbox.model


import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast_id")
    val castId: Int = 0,
    @SerializedName("character")
    val character: String = "",
    @SerializedName("credit_id")
    val creditId: String = "",
    @SerializedName("gender")
    val gender: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("profile_path")
    val profilePath: String = ""
)