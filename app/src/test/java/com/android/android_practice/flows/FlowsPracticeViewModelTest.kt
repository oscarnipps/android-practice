package com.android.android_practice.flows

import com.android.android_practice.coroutines.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherRule::class)
class FlowsPracticeViewModelTest {

    private lateinit var flowsPracticeViewModel: FlowsPracticeViewModel

    @BeforeEach
    fun setUp() {
        flowsPracticeViewModel = FlowsPracticeViewModel()
    }

    @Test
    fun `when uiState1 is retrieved expect items list`() = runTest {
        flowsPracticeViewModel.getUiState1()

        //because there's some delay in the getItems1() method
        advanceUntilIdle()

        assert(flowsPracticeViewModel.uiState1.value.size == 3)
    }

    @Test
    fun `when uiState1 is retrieved expect items list using turbine`() = runTest {
        flowsPracticeViewModel.getUiState1()

        //because there's some delay in the getItems1() method
        advanceUntilIdle()

        assert(flowsPracticeViewModel.uiState1.value.size == 3)
    }

    @Test
    fun `when uiState2 is retrieved expect items list`() = runTest{
        //due to the stateIn or shareIn when converting flows to stateFlows
        //there has to be a collector
        backgroundScope.launch (UnconfinedTestDispatcher()){
            flowsPracticeViewModel.uiState2.collect()
        }

        //because there's some delay in the getItems1() method
        advanceUntilIdle()

        assert(flowsPracticeViewModel.uiState2.value.size == 4)
    }

    @AfterEach
    fun tearDown() {
    }
}