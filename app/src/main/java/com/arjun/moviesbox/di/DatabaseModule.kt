package com.arjun.moviesbox.di

import android.content.Context
import com.arjun.moviesbox.db.MovieDao
import com.arjun.moviesbox.db.MovieDatabase
import com.arjun.tmdb.db.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase =
        MovieDatabase.getInstance(context)

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao =
        database.movieDao


    @Provides
    fun provideRemoteKeysDao(database: MovieDatabase): RemoteKeysDao =
        database.remoteKeysDao

}