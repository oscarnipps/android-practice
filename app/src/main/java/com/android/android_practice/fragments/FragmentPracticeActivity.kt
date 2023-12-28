package com.android.android_practice.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.android.android_practice.R

class FragmentPracticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fragment_practice)

        val fragmentManager = supportFragmentManager.beginTransaction()

        fragmentManager.replace(R.id.container_view,FragmentA::class.java,null)
            .addToBackStack("")
            .commit()

    }
}