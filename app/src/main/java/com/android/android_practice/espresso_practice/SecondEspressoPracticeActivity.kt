package com.android.android_practice.espresso_practice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.android_practice.R
import com.android.android_practice.databinding.ActivitySecondEspressoPracticeBinding

class SecondEspressoPracticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondEspressoPracticeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_second_espresso_practice)

        intent.extras?.let {
            binding.studentName.text = it.getString("name" )
            binding.studentId.text = it.getInt("id" ).toString()
            binding.studentAge.text = it.getInt("age" ).toString()
        }
    }
}