package com.android.android_practice

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import java.util.concurrent.atomic.AtomicBoolean


class PracticeAppEspressoIdlingResource(private val espressoResourceName : String)  : IdlingResource {

    private val isIdle = AtomicBoolean(true)

    @Volatile private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = espressoResourceName

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = callback
    }

    override fun isIdleNow(): Boolean = isIdle.get()

    fun setIdle(isIdleNow: Boolean) {
        if (isIdleNow == isIdle.get()) return
        isIdle.set(isIdleNow)
        if (isIdleNow) {
            resourceCallback?.onTransitionToIdle()
        }
    }
}

object PracticeEspressoCountingIdlingResource{
    private const val RESOURCE_NAME = "CountingIdlingResource"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE_NAME)

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

    fun increment() {
        countingIdlingResource.increment()
    }
}