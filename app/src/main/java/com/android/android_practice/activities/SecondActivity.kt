package com.android.android_practice.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.android_practice.R
import timber.log.Timber

class SecondActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Timber.d("onCreate called")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart called")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d("onRestart called")

    }
    override fun onResume() {
        super.onResume()
        Timber.d("onResume called")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause called")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy called")
    }
}