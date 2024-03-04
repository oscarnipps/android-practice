package com.android.android_practice.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PracticeViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    //saved state handle can also retrieve /save custom objects but those objects have to be parcelables
    //saved state handle can also retrieve a flow / livedata i.e
    //val stateFlowFromSavedStateHandle = savedStateHandle.getStateFlow("counter",0)
    //val livedataFromSavedStateHandle = savedStateHandle.getLiveData("counter",0)

    private val _uiState = MutableStateFlow(
        UiState(
            counter = savedStateHandle["counter"] ?: 0 //  handle process death for the counter value
        )
    )
    val uiState = _uiState.asStateFlow()

    fun updateCounter() {
        val updatedCounter = uiState.value.counter + 1

        /*
         *  using this format could have potential problems in a multi-threaded environment
         *  i.e concurrency issues like race conditions where more than one thread is retrieving / accessing the data
         */
        _uiState.value = uiState.value.copy(
            counter = updatedCounter
        )

        //best to use the update format , as it'd prevent running into race conditions :
        //_uiState.update { it.copy(counter = updatedCounter) }
    }

    fun updateCounterViaSavedStateHandle() {
        val updatedCounter = uiState.value.counter + 1

        /*
         *  using this format could have potential problems in a multi-threaded environment
         *  i.e concurrency issues like race conditions where more than one thread is retrieving / accessing the data
         */
        savedStateHandle["counter"] = uiState.value.counter + 1
        _uiState.value = uiState.value.copy(
            counter = updatedCounter
        )

        //best to use the update format , as it'd prevent running into race conditions :
        //_uiState.update { it.copy(counter = updatedCounter) }
    }
}