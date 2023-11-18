package com.android.android_practice

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ProcessLifecycleOwner
import com.android.android_practice.lifecycle.ActivityLogger
import com.android.android_practice.lifecycle.LoggerA
import timber.log.Timber

class PracticeApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        LoggerA(
            ProcessLifecycleOwner.get().lifecycle
        )

        registerActivityLifecycleCallbacks(ActivityLogger())
    }
}