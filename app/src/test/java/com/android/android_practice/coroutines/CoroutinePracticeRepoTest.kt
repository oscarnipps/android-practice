package com.android.android_practice.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinePracticeRepoTest(){

    private lateinit var repo : CoroutinePracticeRepo

    @BeforeEach
    fun setUp() {
       repo = CoroutinePracticeRepo()
    }

    @Test
    fun `shop items 1 should return a list of shopping items`() = runTest {
        val actualItems = repo.getShoppingItems1()

        assertTrue(actualItems.isNotEmpty())
    }

    @Test
    fun `get shopping items With switched context should return a list of shopping items`() = runTest (){
        /*
        * - all coroutines within the  runTest{..} run in a 'TestScope' (all suspend functions and coroutines must run within a coroutine scope)
        *   which internally uses a 'TestDispatcher' that is responsible for the threading and scheduling and the TestDispatcher Uses a 'TestCoroutineScheduler'
        *
        * - TestDispatcher is just an interface and is of 2 types :
        *   -> StandardTestDispatcher : queues work / coroutines on the scheduler and also can be advanced manually
        *   -> UnconfinedTestDispatcher : is used as the main dispatcher aka for the main thread and also used for coroutines that collect flows
        *
        * - when actual code has operations running in a separate thread other than the test thread (i.e TestDispatcher within the TestScope)
        *  you need to use StandardTestDispatcher which queues up the work on the scheduler so it's best to always inject dispatchers where needed
        *
        * - advanceBy() , runCurrent() and advanceUntilIdle() are used to modify virtual time within the scheduler
        *
        * - advanceBy : advances (and runs) the queued up coroutines my a certain amount of milliseconds
        *
        * - runCurrent : runs the current coroutines queued up
        *
        * - advanceUntilIdle : runs all the current coroutines queued in the scheduler
        *
        * - delays are automatically skipped when using runTest{...} , however when threads are changed (i.e switching dispatchers) then the delays
        *   are no longer switched because threading would to be handled by the switched thread
        *
        * -  multiple test dispatchers must always share the same scheduler ( testScheduler)
        *
        * -  'backgroundScope' : anything launched in this scope will automatically be cancelled at the end of the test
        *
        */

        //would have taken 15 seconds if the StandardTestDispatcher was not used as during the execution within the repo class it switched threads
        repo = CoroutinePracticeRepo(
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )

        val actualItems = repo.getShoppingItemsWithSwitchedContext()

        assertTrue(actualItems.isNotEmpty())
    }

}