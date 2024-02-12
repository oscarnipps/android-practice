package com.android.android_practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.android_practice.activities.FirstActivity
import com.android.android_practice.fragments.FragmentPracticeActivity
import com.android.android_practice.lifecycle.LifecyclePracticeActivity
import com.android.android_practice.navigation_component.NavigationPracticeActivity
import com.android.android_practice.permissions.PermissionActivity
import com.android.android_practice.recyclerview.RecyclerViewPracticeActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("onCreate called")
    }

    fun showActivitiesPractice(view: View) {
        Intent(this , FirstActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun showLifecyclePractice(view: View) {
        Intent(this , LifecyclePracticeActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun showPermissionsPractice(view: View) {
        Intent(this , PermissionActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun showFragmentsPractice(view: View) {
        Intent(this , FragmentPracticeActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun showRecyclerViewPractice(view: View) {
        Intent(this , RecyclerViewPracticeActivity::class.java).apply {
            startActivity(this)
        }
    }


    fun showNavigationPractice(view: View) {
        Intent(this , NavigationPracticeActivity::class.java).apply {
            startActivity(this)
        }
    }

}