package com.android.android_practice.activities

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.android.android_practice.R
import timber.log.Timber

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_first)

        Timber.d("onCreate called")

        Timber.d(savedInstanceState?.getString("stateBundleKey"))

        //using activity result api to take picture and return result back
        //i.e calling showImagePickerUsingActivityContract()

        //using activity result api with custom contract to get result back from activity
        useCustomActivityContract()
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

    //pre-built activity contract to get result back from another activity
    fun showImagePickerUsingActivityContract() {
        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { isImageCaptureSuccessful ->
                if (isImageCaptureSuccessful) {
                    //do required modifications
                }
            }

        val uriToSaveImageTaken = ""

        activityResultLauncher.launch(Uri.parse(uriToSaveImageTaken))
    }

    //custom activity contract to get result back from another activity
    private fun useCustomActivityContract() {
        val activityResultLauncher = registerForActivityResult(CustomResultContract()){userCategory ->
            Timber.d("user category from activity result : $userCategory")
        }

        activityResultLauncher.launch(1)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.d("onSaveInstanceState called")
        outState.putString("stateBundleKey", "state being used")
    }

    //this is called after onStart() method only if the bundle is not empty/null (no need to check for nulls)
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