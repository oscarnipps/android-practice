package com.android.android_practice

import android.app.Application
import timber.log.Timber

class PracticeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}