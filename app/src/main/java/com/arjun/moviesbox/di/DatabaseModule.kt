package com.arjun.moviesbox.di

import android.content.Context
import com.arjun.moviesbox.db.MovieDatabase
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

}