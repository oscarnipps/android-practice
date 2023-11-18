package com.android.android_practice.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.android_practice.R

class LifecyclePracticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_practice)

        LoggerA(lifecycle)

        LoggerB(lifecycle)

        LoggerC(lifecycle)
    }
}