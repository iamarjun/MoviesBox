package com.arjun.moviesbox

import android.app.Application
import timber.log.Timber

class MovieBoxApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}