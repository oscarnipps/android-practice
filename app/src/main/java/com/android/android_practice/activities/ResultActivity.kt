package com.android.android_practice.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.android_practice.R

class ResultActivity : AppCompatActivity() {
    private var level : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result)

        level = intent.getIntExtra(CustomResultContract.USER_LEVEL_KEY,0)
    }

     fun finishResultUpload(view : View) {
        val category = if (level > 2) "standard" else "basic"

        val resultIntent = Intent().putExtra(CustomResultContract.USER_CATEGORY_KEY,category )

        setResult(Activity.RESULT_OK,resultIntent)

        finish()
    }
}