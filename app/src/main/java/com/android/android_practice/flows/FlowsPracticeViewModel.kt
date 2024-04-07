package com.android.android_practice.flows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlowsPracticeViewModel : ViewModel() {

    private val repo = FlowPracticeRepo()

    /////// handles events
    private val _uiEvent1 = MutableSharedFlow<String>(replay = 1)
    val uiEvent1 = _uiEvent1.asSharedFlow()

    private val _uiEvent2 = MutableSharedFlow<String>()
    val uiEvent2 = _uiEvent2.asSharedFlow()
    /////////

    ////// handles state
    private val _uiState1 = MutableStateFlow<List<String>>(emptyList())
    val uiState1 = _uiState1.asStateFlow()

    //stateIn(...) converts a flow to a stateFlow
    //could have zero or more collectors
    val uiState2 = repo.getItems2().stateIn(
        scope = viewModelScope, //coroutine scope
        initialValue = emptyList(), //would have probably used a flag (i.e loading)
        started = SharingStarted.WhileSubscribed(5000) //upstream flows would stop producing if 5 seconds passes and there aro no collectors active
    )

    val uiState4 = repo.flowItems1.stateIn(
        scope = viewModelScope, //coroutine scope
        initialValue = 0, //would have probably used a flag (i.e loading)
        started = SharingStarted.WhileSubscribed(5000)
    )
    ///////

    fun getUiState1() {
        viewModelScope.launch{
            _uiState1.update { repo.getItems1() }
        }
    }

    fun sendEvent1(event : String) {
        viewModelScope.launch {
            _uiEvent1.emit(event)
        }
    }

    fun showFlowOperator(): Flow<String> {
       return repo.showFlowOperator()
    }
}