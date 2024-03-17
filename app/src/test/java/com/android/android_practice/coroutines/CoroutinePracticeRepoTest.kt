package com.android.android_practice.coroutines

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue

class CoroutinePracticeRepoTest(){

    private lateinit var repo : CoroutinePracticeRepo

    @Before
    fun setUp() {
       repo = CoroutinePracticeRepo()
    }

    @Test
    fun `shop items 1 should return a list of shopping items`() = runTest {
        val actualItems = repo.getShoppingItems1()

        assertTrue(actualItems.isNotEmpty())
    }

    @Test
    fun `get shopping items With switched context should return a list of shopping items`() = runTest {
        /*
        * -when actual code has operations running in a separate thread other than the test thread (i.e TestDispatcher within the TestScope)
        *  you need to use StandardTestDispatcher which queues up the work on the scheduler
        *
        * - StandardTestDispatcher : queues work / coroutines on the scheduler and also can be advanced manually
        *
        * - UnconfinedTestDispatcher : is used as the main dispatcher aka for the main thread
        *
        * - advanceBy() , runCurrent() and advanceUntilIdle() are used to modify virtual time within the scheduler
        *
        * - advanceBy : advances (and runs) the queued up coroutines my a certain amount of milliseconds
        *
        * - runCurrent : runs the current coroutines queued up
        *
        * - advanceUntilIdle : runs all the current coroutines queued in the
        */

        //would have taken 15 seconds if the StandardTestDispatcher was not used
        repo = CoroutinePracticeRepo(
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )

        val actualItems = repo.getShoppingItemsWithSwitchedContext()

        assertTrue(actualItems.isNotEmpty())
    }

}