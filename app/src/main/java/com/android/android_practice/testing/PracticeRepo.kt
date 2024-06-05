package com.android.android_practice.testing

class PracticeRepo {
    private val users = mutableMapOf(
      1 to  "james bob",
      2 to  "daniel meek",
      3 to  "harry kane"
    )
    fun getUserName(id : Int) : String{
        return users[id] ?: "user not found"
    }

}