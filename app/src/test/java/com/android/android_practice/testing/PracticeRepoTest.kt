package com.android.android_practice.testing

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PracticeRepoTest{

    private lateinit var repo : PracticeRepo

    @BeforeEach
    fun setUp() {
        repo = PracticeRepo()
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun `when userId exists in cache expect userName`() {
        val actual = repo.getUserName(1)
        Assertions.assertTrue(actual.isNotEmpty())
    }

    @Test
    fun `given userId not in the users list when getUsers is called then return an empty username`() {
        //
        val actual = repo.getUserName(100)
        Assertions.assertTrue(actual.equals("user not found",true))
    }
}