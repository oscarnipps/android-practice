package com.android.android_practice.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinesPracticeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CoroutinesPracticeViewModel

    @Before
    fun setUp() {
        viewModel = CoroutinesPracticeViewModel()
    }

    @Test
    fun `shop items 3 should return a list of shopping items`() = runTest {
        viewModel.getShoppingItems3()
        advanceUntilIdle()
        Assertions.assertTrue(viewModel.itemsState.value.isNotEmpty())
    }


    @After
    fun tearDown() {
    }
}