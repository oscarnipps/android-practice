package com.android.android_practice.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import timber.log.Timber

/*
NOTES :
class which log activity events using the ActivityLifecycleCallbacks api where the event is
propagated via the callbacks and is registered in the application class using 'registerActivityLifecycleCallbacks'
*/
open class ActivityLogger : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onActivityStarted(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityResumed(activity: Activity) {
        Timber.d("activity class name : ${activity.localClassName}")
        Timber.d("activity component : ${activity.componentName}")
    }

    override fun onActivityPaused(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityStopped(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        TODO("Not yet implemented")
    }

    override fun onActivityDestroyed(activity: Activity) {
        TODO("Not yet implemented")
    }
}