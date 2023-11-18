package com.android.android_practice.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber


/*
NOTES :
class which log events using the LifecycleObserver api ,where the event is propagated via the
onStateChanged callback which can then be used to monitor the event changes
*/
class LoggerC ( lifecycle : Lifecycle) : LifecycleEventObserver {

    init {
        lifecycle.addObserver(this)
    }

    //when the state changes do something
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                Timber.d("logger C started")
            }
            Lifecycle.Event.ON_STOP -> {
                Timber.d("logger C stopped")
            }
            Lifecycle.Event.ON_CREATE -> {}
            Lifecycle.Event.ON_RESUME -> {}
            Lifecycle.Event.ON_PAUSE -> {}
            Lifecycle.Event.ON_DESTROY -> {}
            Lifecycle.Event.ON_ANY -> {}
        }
    }
}