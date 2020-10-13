package com.arjun.moviesbox.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RemoteKeys")
data class RemoteKeys(
    @PrimaryKey
    val movieId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)