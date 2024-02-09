package com.android.android_practice.navigation_component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.android_practice.R

/*
* practice using navigation components to showcase certain scenarios
*
* scenario : navigate from graphADesA to graphADesB
* scenario : navigate from graphADesA to graphADesB with arguments
* scenario : navigate from graphADesA to graphBDesA to graphCDesA then back to graphADesA with data and
* with backstack cleared
* scenario : navigate from graphADesA to graphCDesA to globalDesA (with data)
* scenario : deeplink to graphCDesB
* scenario : navigate from graphADesA to graphBDesB ( graphBDesB is a non-start destination in graph B)
* with data
*/
class NavigationPracticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_practice)
    }
}