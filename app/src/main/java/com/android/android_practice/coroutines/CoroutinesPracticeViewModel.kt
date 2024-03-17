package com.android.android_practice.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class CoroutinesPracticeViewModel : ViewModel() {

    private val _itemsState: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val itemsState = _itemsState.asStateFlow()

    fun getShoppingItems3() {
        viewModelScope.launch {
            delay(2000)

            Timber.d("items 3 work done")

            _itemsState.value = listOf("bottles", "buckets")
        }
    }

    fun getShoppingItemsWithSwitchedContext() {
        viewModelScope.launch {
            val result = CoroutinePracticeRepo().getShoppingItemsWithSwitchedContext()
            _itemsState.value = result
        }
    }
}