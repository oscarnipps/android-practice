package com.android.android_practice.lifecycle

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

/*
NOTES :
class which log events
using the DefaultLifecycleObserver api , you can override the required lifecycle method
as it already implements LifecycleObserver
*/
class LoggerA( lifecycle : Lifecycle) : DefaultLifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Timber.d("logger A started")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Timber.d("logger A stopped")
    }
}