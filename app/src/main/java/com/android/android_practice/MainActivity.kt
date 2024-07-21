package com.android.android_practice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.android_practice.activities.FirstActivity
import com.android.android_practice.coroutines.CoroutinesPracticeActivity
import com.android.android_practice.espresso_practice.EspressoPracticeActivity
import com.android.android_practice.firebase_practice.FirebasePracticeActivity
import com.android.android_practice.flows.FlowsPracticeActivity
import com.android.android_practice.fragments.FragmentPracticeActivity
import com.android.android_practice.lifecycle.LifecyclePracticeActivity
import com.android.android_practice.navigation_component.NavigationPracticeActivity
import com.android.android_practice.permissions.PermissionActivity
import com.android.android_practice.recyclerview.RecyclerViewPracticeActivity
import com.android.android_practice.viewmodel.ViewModelPracticeActivity
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

    fun showViewModelPractice(view: View) {
        Intent(this , ViewModelPracticeActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun showCoroutinesPractice(view: View) {
        Intent(this , CoroutinesPracticeActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun showFlowsPractice(view: View) {
        Intent(this , FlowsPracticeActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun showEspressoPractice(view: View) {
        Intent(this , EspressoPracticeActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun showFirebasePractice(view: View) {
        Intent(this , FirebasePracticeActivity::class.java).apply {
            startActivity(this)
        }
    }
}