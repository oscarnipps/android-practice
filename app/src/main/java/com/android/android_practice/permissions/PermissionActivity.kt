package com.android.android_practice.permissions

import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.android_practice.R
import timber.log.Timber

class PermissionActivity : AppCompatActivity() {

    private val requestType = "single"

    private val singlePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionGranted ->
        }

    private val multiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grantedPermissionsMap ->
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
    }

    fun getUserPermission(view: View) {
        requestPermission()
    }

    /*
    * flow :
    *   -check if permission is granted
    *   -should should the rationale dialog if the permission was denied
    *   -the permission has not being granted , so you ask for it
    */
    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                //permission has been granted so carry out required action
                Timber.d("permission has been granted !!")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this, permission.CAMERA) -> {
                //show custom dialog to inform why the permission is required
                Timber.d("denied permission but here's why we need the permission man... ")
            }

            else -> {

                if (requestType == "single") {
                    singlePermissionLauncher.launch(permission.CAMERA)
                    return
                }

                multiplePermissionsLauncher.launch(
                    arrayOf(
                        permission.CAMERA,
                        permission.READ_CONTACTS,
                        permission.BLUETOOTH,
                    )
                )
            }
        }
    }
}