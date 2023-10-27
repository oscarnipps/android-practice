package com.android.android_practice.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class CustomResultContract : ActivityResultContract<Int,String>() {

    companion object{
        const val USER_CATEGORY_KEY = "category"
        const val USER_LEVEL_KEY = "level"
    }


    override fun createIntent(context: Context, passedLevelValue: Int): Intent {
        //use passed value which is same as the generic type of the overridden ActivityResultContract class
        //first generic parameter i.e " <Int "
       return Intent(context,ResultActivity::class.java).putExtra(USER_LEVEL_KEY , passedLevelValue)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        if (resultCode == Activity.RESULT_OK) {
            return intent?.let {
                it.getStringExtra(USER_CATEGORY_KEY)
            } ?: ""
        }

        return ""
    }
}