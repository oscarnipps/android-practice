package com.android.android_practice.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherRule::class)
class
CoroutinesPracticeViewModelTest {

    //use when you're working with junit4 ( the class needs to inherit from 'TestWatcher' and override starting and finishing methods)
    //@get:Rule
    //val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CoroutinesPracticeViewModel

    @BeforeEach
    fun setUp() {
        viewModel = CoroutinesPracticeViewModel()
    }

    @Test
    fun `shop items 3 should return a list of shopping items`() = runTest {
        viewModel.getShoppingItems3()
        advanceUntilIdle()
        Assertions.assertTrue(viewModel.itemsState.value.isNotEmpty())
    }


    @AfterEach
    fun tearDown() {
    }
}