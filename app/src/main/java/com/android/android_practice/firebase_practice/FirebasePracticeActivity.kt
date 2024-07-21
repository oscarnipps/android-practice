package com.android.android_practice.firebase_practice

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.android_practice.R
import com.android.android_practice.databinding.ActivityFirebasePracticeBinding
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import timber.log.Timber

class FirebasePracticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirebasePracticeBinding

    private lateinit var remoteConfig: FirebaseRemoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_firebase_practice)

        setUpFirebaseRemoteConfig()

        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.feature1Btn.setOnClickListener {
            Firebase.analytics.logEvent("feature_1_button_click_event"){
                param("buttonClick","true")
            }
        }

        binding.feature2Btn.setOnClickListener {

        }

        binding.feature3Btn.setOnClickListener {

        }
    }

    private fun setUpFirebaseRemoteConfig() {
        remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600 //how often remote config should check for new values and apply them i.e 1hr (3600 seconds)
        }

        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults) //completes almost immediately

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.d("FirebasePracticeActivity" , "remote config parameters updated")
                updateViewsWithConfigValues()
            }else{
                Timber.d("FirebasePracticeActivity" , "failed to update remote config parameters")
                updateViewsWithDefaultValues()
            }
        }
    }

    private fun updateViewsWithDefaultValues() {
        binding.feature1Btn.visibility = if (remoteConfig.getBoolean(FeatureFlags.FEATURE_1_BUTTON)) View.VISIBLE else View.INVISIBLE
        binding.feature2Btn.visibility = if (remoteConfig.getBoolean(FeatureFlags.FEATURE_2_BUTTON)) View.VISIBLE else View.INVISIBLE
        binding.feature3Btn.visibility = if (remoteConfig.getBoolean(FeatureFlags.FEATURE_3_BUTTON)) View.VISIBLE else View.INVISIBLE
    }

    private fun updateViewsWithConfigValues() {
        binding.feature1Btn.visibility = if (remoteConfig.getBoolean(FeatureFlags.FEATURE_1_BUTTON)) View.VISIBLE else View.INVISIBLE
        binding.feature2Btn.visibility = if (remoteConfig.getBoolean(FeatureFlags.FEATURE_2_BUTTON)) View.VISIBLE else View.INVISIBLE
        binding.feature3Btn.visibility = if (remoteConfig.getBoolean(FeatureFlags.FEATURE_3_BUTTON)) View.VISIBLE else View.INVISIBLE
    }

}