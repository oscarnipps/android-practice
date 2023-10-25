package com.android.android_practice.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.android_practice.R
import timber.log.Timber

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        Timber.d("onCreate called")
        Timber.d(savedInstanceState?.getString("stateBundleKey"))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Timber.d("landscape mode !!")
        }

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Timber.d("portrait mode !!")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("onSaveInstanceState called")
        outState.putString("stateBundleKey" , "state being used")
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.d("onRestoreInstanceState called")
        Timber.d(savedInstanceState.getString("stateBundleKey"))
    }

    fun proceedToSecondActivity(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
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