package com.android.android_practice.coroutines

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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
* - to use a callback within a coroutine use either (NOTE : 'suspendCoroutine' and 'suspendCancellableCoroutine' are both used for one-shot callbacks but for continuous stream use 'callbackFlow') :
*       -> suspendCoroutine{... } : where you wrap the callback method in a suspendCoroutine block and either resume via the 'continuation' value or resume with exception
*          i.e continuation.resume(callbackSuccessResult or callbackErrorResult) or continuation.resumeWithException(callbackErrorResult)
*
*      -> suspendCancellableCoroutine{... } : same as the "suspendCoroutine" counterpart but it offers cancellation extras , meaning you can specify what happens when the callback coroutine is cancelled
*          i.e continuation.invokeOnCancellation { ... }. Note that you must specify it within the "suspendCancellableCoroutine" block
*
*     -> callbackFlow{... } : used for continuous stream of data
*/
class CoroutinesPracticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutinesPracticeBinding
    
    //better way would be to inject it via dependency injection
    private val repo = CoroutinePracticeRepo()

    private val viewModel by viewModels<CoroutinesPracticeViewModel>()

    private val api : Api = Api()


    private val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        //check and handle the type of exception
        if (throwable is IllegalArgumentException) {
            Timber.d("IllegalArgumentException caught : ${throwable.localizedMessage}")
            return@CoroutineExceptionHandler
        }

        Timber.d("exception caught : ${throwable.localizedMessage}")
        //showErrorView(throwable.localizedMessage ?: "")
    }

    private val customSupervisorScope =
        CoroutineScope(SupervisorJob() + CoroutineName("CustomName") + coroutineExceptionHandler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_coroutines_practice)

        //showCustomCoroutineScope()

        viewModel.getShoppingItems3()

        showViewModelScope()
    }

    private fun showGlobalScope() {
        GlobalScope.launch {
            val items = repo.getShoppingItems1()

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
                val result = async { repo.getShoppingItems1() }

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
            combinedResult.plus( repo.getShoppingItemsWithError())
        }

        customSupervisorScope.launch {
            combinedResult.plus( repo.getShoppingItems1()())
        }

        customSupervisorScope.launch {
            combinedResult.plus( getShoppingItems2())
        }

        updateUi(combinedResult)*/

        customSupervisorScope.launch {
            supervisorScope {
                launch { combinedResult.addAll(repo.getShoppingItemsWithError()) }.join()

                launch { combinedResult.addAll(repo.getShoppingItems1()) }.join()

                launch { combinedResult.addAll(repo.getShoppingItems2()) }.join()

                withContext(Dispatchers.Main){
                    updateUi(combinedResult)
                }
            }

        }
    }

    private fun showViewModelScope() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.itemsState.collectLatest {
                    updateUi(it)
                }
            }
        }
    }

    private fun showErrorHandlingWithTryCatch() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    val items = repo.getShoppingItemsWithError()
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
                    repo.getShoppingItemsWithError()
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

                val items = repo.getShoppingItemsWithError()

                updateUi(items)
            }
        }
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

    private fun showCancellableCallBackCoroutine() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val result = coroutineCancellableCallback()
                println(result)
            }
        }
    }

    private fun showCallBackFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flowCallback().collect{ flowResult ->
                    println(flowResult)
                }
            }
        }
    }

    private suspend  fun coroutineCancellableCallback(): String = suspendCancellableCoroutine { continuation ->
        val callback = object : ResultCallback { // Implementation of some callback interface
            override fun onCompleted(result: String) {
                // Resume coroutine with a value provided by the callback
                continuation.resume(result)
            }
            override fun onError(cause: Throwable) {
                // Resume coroutine with an exception provided by the callback
                continuation.resumeWithException(cause)
            }
        }

        // Register callback with an API
        api.register(callback)

        // Remove callback on cancellation
        continuation.invokeOnCancellation { api.unregister(callback) }
        // At this point the coroutine is suspended by suspendCancellableCoroutine until callback fires
    }

    private suspend fun coroutineCallback() = suspendCoroutine<String> { continuation ->
        val callback = object : ResultCallback{
            override fun onCompleted(result: String) {
                continuation.resume(result)
            }

            override fun onError(cause: Throwable) {
                continuation.resumeWithException(cause)
            }

        }
    }


    private suspend  fun flowCallback() = callbackFlow<String> {
        val callback = object : ResultCallback { // Implementation of some callback interface
            override fun onCompleted(result: String) {
                //send updated result (in this case success)
                trySend(result)
            }
            override fun onError(cause: Throwable) {
                //send updated result (in this case error)
                trySend(cause.toString())
            }
        }

        //handle clean up when the callback flow is closed
        awaitClose { api.unregister(callback) }
    }

    interface ResultCallback{
        fun  onCompleted(result : String)
        fun onError(cause: Throwable)
    }

    class Api{
        fun register(callback: ResultCallback){}

        fun unregister(callback: ResultCallback){}
    }



}