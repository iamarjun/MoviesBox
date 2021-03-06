package com.arjun.moviesbox.model


import com.google.gson.annotations.SerializedName

data class Similar(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<Any> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)