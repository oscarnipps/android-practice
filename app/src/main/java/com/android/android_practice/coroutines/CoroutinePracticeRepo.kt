package com.android.android_practice.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber

class CoroutinePracticeRepo(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {


    suspend fun getShoppingItems1(): List<String> {
        delay(5000)

        Timber.d("items 1 work done")

        return listOf("shoes", "socks", "necklace")
    }

    suspend fun getShoppingItems2(): List<String> {
        delay(3000)

        Timber.d("items 2 work done")

        return listOf("shirt", "boxes")
    }

    suspend fun getShoppingItemsWithError(): List<String> {
        delay(1000)
        throw IllegalArgumentException("error getting items 3")
    }

    suspend fun getShoppingItemsWithSwitchedContext(): List<String> {
        return withContext(ioDispatcher) {
            Timber.d("fetching ...")

            delay(15000)

            Timber.d("getShoppingItemsWithSwitchedContext items work done")

            listOf("keyboard, mouse")
        }
    }


}