package com.android.android_practice.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

/*
NOTES :
class which log events using the LifecycleObserver api ,where you use annotations to depict which
lifecycle event or state you want react to via '@OnLifecycleEvent(...)'
*/
class LoggerB ( lifecycle : Lifecycle) :LifecycleObserver {


    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
     private fun onStart() {
        Timber.d("logger B started")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
     private fun onStop() {
        Timber.d("logger B stopped")
    }
}