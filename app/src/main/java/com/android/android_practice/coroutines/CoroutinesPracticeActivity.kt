package com.android.android_practice.coroutines

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.android_practice.R
import com.android.android_practice.databinding.ActivityCoroutinesPracticeBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import timber.log.Timber

/*
* Notes :
*
* - coroutines always run within a coroutine scope i.e global scope , viewmodelScope , lifecycleScope or a custom scope
*
* - can only run a coroutine within a suspend function or a coroutine scope
*
* - coroutine scope :
*       -> keeps track of it's coroutines
*       -> gets notified when a failure happens
*       -> has the ability to cancel ongoing work ( i.e  contained / child coroutines running within the scope)
*
* - coroutines started within a global scope would live as long as the app is still alive (if the app is killed , ongoing coroutines within this scope would also be killed)
*
* - job basically provides the lifecycle of the coroutine
*
* - job has states of the coroutine which are not accessible ( new , active , completing , cancelling , cancelled and completed )
*
* - job has properties that reflect the state of the coroutine which are accessible ( isActive , isCancelled , isCompleted )
*
* - coroutine context basically describes the behaviour of a coroutine. consists of :
*       -> Dispatcher : which determines the threading
*       -> Job : determines the lifecycle
*       -> Coroutine Name : used for debugging purpose
*       -> Coroutine Exception Handler : handles exceptions propagated
*
* - dispatchers consist of :
*       -> Dispatcher.Main : operations run on the main ui thread
*       -> Dispatcher.IO : operations run on a separate thread used for IO computations (i.e network , database)
*       -> Dispatcher.UNCONFINED : operations run on the default thread it was started on (NOTE : can also specify thread using 'newSingleThreadContext()' )
*       -> Dispatcher.DEFAULT : complex and long running operations run on a this thread
*
* - can be started by either launch{..} or async{..} (launch returns a job while async returns a Deferred type which can be used to await the result of the coroutine)
*
* - coroutine is started eagerly that is it enters the active state by default as soon as it is launched
*
* - async{} can also be used to launch a coroutine , it returns a deferred type that can be retrieved by calling await()
*
* - coroutine can be cancelled by calling .cancel()
*
* - when parent context is of Job() type , it runs in a CoroutineScope
*
* - when parent context is of SupervisorJob() type , it runs in a SupervisorScope
*
* - errors are propagated upwards to the parent coroutine  ( except when in a supervisorScope as it does not propagate the error / exception upwards)
*
* - various ways of handling exceptions within a coroutine:
*       -> try/catch : wrapping the code that could throw an exception within a try/catch
*       -> runCatching : wrapping the code that could throw an exception within the runCatching{..} block which returns a 'Result' type (i.e result.isSuccess or result.isFailure)
*       -> coroutineExceptionHandler : handles exceptions thrown within a coroutine ( installed att the context level or parent level)
*
* - when a coroutine is cancelled it throws a cancellation exception and cancellation needs to be cooperative (handled in a graceful way) by :
*       -> isActive : does an action before finishing the coroutine
*       -> ensureActive : checks if the coroutine is still active under the hood but it instantly stops the coroutine if it's been cancelled
*
* - when a child coroutine throws an exception (other than a CancellationException caused when a coroutine is cancelled) within a CoroutineScope , all other sibling coroutines within that scope is cancelled by the scope
*
* - when a child coroutine throws an exception (other than a CancellationException caused when a coroutine is cancelled) within a SupervisorScope , other sibling coroutines within that scope are not cancelled by the scope
*
* - viewmodelScope is of supervisorScope as it uses a SupervisorJob() under the hood
*
* - for async the error is thrown when await() is called so error needs to be handled at that point
*
*/
class CoroutinesPracticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutinesPracticeBinding


    private val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        Timber.d("exception caught : ${throwable.localizedMessage}")
        //showErrorView(throwable.localizedMessage ?: "")
    }

    private val customSupervisorScope =
        CoroutineScope(SupervisorJob() + CoroutineName("CustomName") + coroutineExceptionHandler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutines_practice)

        showCustomCoroutineScope()
    }

    private fun showGlobalScope() {
        lifecycleScope.launch {
            val items = getShoppingItems1()

            updateUi(items)
        }
    }

    private fun showRunBlocking() {
        //this blocks the main UI thread
        runBlocking {
            //operations i.e coroutine functions or other suspend functions
        }
    }

    private fun showAsyncAwait() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val result = async { getShoppingItems1() }

                val items = result.await()

                updateUi(items)
            }
        }
    }

    private fun showCustomCoroutineScope() {
        val combinedResult = mutableListOf<String>()

        //can also chain child coroutines like this (i.e calling individual .launch() on the customSupervisorScope )
        /*customSupervisorScope.launch {
            //this would throw an exception but because it's in a supervisor scope it would not cancel it's siblings coroutines
            combinedResult.plus( getShoppingItemsWithError())
        }

        customSupervisorScope.launch {
            combinedResult.plus( getShoppingItems1())
        }

        customSupervisorScope.launch {
            combinedResult.plus( getShoppingItems2())
        }

        updateUi(combinedResult)*/

        customSupervisorScope.launch {
            supervisorScope {
                launch { combinedResult.addAll(getShoppingItemsWithError()) }.join()

                launch { combinedResult.addAll(getShoppingItems1()) }.join()

                launch { combinedResult.addAll(getShoppingItems2()) }.join()

                withContext(Dispatchers.Main){
                    updateUi(combinedResult)
                }
            }

        }
    }


    private fun showErrorHandlingWithTryCatch() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    val items = getShoppingItemsWithError()
                } catch (e: Exception) {
                    showErrorView(e.localizedMessage ?: "")
                }
            }
        }
    }

    private fun showErrorHandlingWithRunCatching() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val items = runCatching {
                    getShoppingItemsWithError()
                }

                if (items.isSuccess) {
                    //proceed with happy scenario
                }

                if (items.isFailure) {
                    val exception = items.exceptionOrNull()
                    binding.loading.visibility = View.INVISIBLE

                    binding.errorView.visibility = View.VISIBLE

                    Timber.d("exception caught : ${exception?.localizedMessage}")
                }
            }
        }
    }

    private fun showErrorHandlingWithCoroutineExceptionHandler() {
        lifecycleScope.launch(coroutineExceptionHandler) {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                val items = getShoppingItemsWithError()

                updateUi(items)
            }
        }
    }

    private suspend fun getShoppingItems1(): List<String> {
        delay(2000)

        Timber.d("items 1 work done")

        return listOf("shoes", "socks", "necklace")
    }

    private suspend fun getShoppingItems2(): List<String> {
        delay(3000)

        Timber.d("items 2 work done")

        return listOf("shirt", "boxes")
    }

    private suspend fun getShoppingItemsWithError(): List<String> {
        delay(1000)
        throw Exception("error getting items 3")
    }

    private fun updateUi(items: List<String>) {
        binding.loading.visibility = View.INVISIBLE

        binding.items.text = getString(R.string.shopping_items, items)
    }

    private fun showErrorView(errorMessage: String) {
        binding.loading.visibility = View.INVISIBLE

        binding.errorView.visibility = View.VISIBLE

        Timber.d("exception caught : $errorMessage")
    }

}