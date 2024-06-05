package com.android.android_practice.flows

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class FlowPracticeRepo {

    val flowItems1 = flowOf(10, 20, 30, 40, 45, 25, 15, 19, 22)

    private val flowItems2 = flowOf(50, 50, 60, 70, 70, 75, 80)

    private val flowItems3 = flowOf("box", "shoes", "pancakes", "flowers")

    private val flowItems4 = flowOf("oscar", "dave", "daniel", "davies", "susan", "shaw", "N/A")

    private val flowItems5 = flowOf("oscar","dave", "N/A")

    suspend fun getItems1(): List<String> {
        delay(2000)

        //create flows using the flowOf(...) operator
        return listOf("bottles", "flasks", "covers")
    }

    fun getItems2(): Flow<List<String>> {
        //create flows using the flow builder
        return flow {
            delay(2000)

            val items = listOf("tooth-brush", "pen", "books", "table")

            emit(items)

        }
    }

    fun getItems3(): Flow<String> {
        //create flows using the extension method asFlow()
        return listOf("box", "shoes", "pancakes", "flowers").asFlow()
    }

    private fun getFLowWithException(): Flow<String> {
        return flowItems5.map { name ->
            if (name == "N/A") throw IllegalArgumentException() else  name
        }
    }

    fun showFlowOperator(): Flow<String> {
        //return showMapOperator()
        //return showFilterOperator()
        //return showTransformOperator()
        //return showTakeOperator()
        //return showReduceOperator()
        //return showFoldOperator()
        //return showFlatmapConcatOperator()
        //return showFlatmapMergeOperator()
        //return showFlatmapLatestOperator()
        //return showZipOperator()
        //return showCombineOperator()
        //return showOnEachOperator()
        //return showCatchOperator()
        //return showOnStartOperator()
        //return showOnCompletionOperator()
        //return showBufferOperator()
        //return showDebounceOperator()
        //return showConflateOperator()
        //return showDistinctUntilChangedOperator()
        return showRetryOperator()
    }

    //resubscribes to the flow and tries the process again based on the retry parameters ( backOff policy , number of retries)
    //it provides a way to recover from errors and continue processing the Flow
    //can also use 'retryWhen' operator
    //also note that using a 'yufretry' operator after a 'catch' operator in the chains of operator on the flow would not work (has to be before the 'catch' operator)
    private fun showRetryOperator(): Flow<String> {
        return getFLowWithException()
            .retry(2) { cause ->
                Timber.d("retrying...")
                cause is java.lang.IllegalArgumentException
            }.catch { exception -> emit("exception caught : $exception") }
    }

    //filters out consecutive duplicate values
    //it ensures that only distinct, non-consecutive elements are emitted downstream
    private fun showDistinctUntilChangedOperator(): Flow<String> {
        return flowItems2.map { "score : $it" }.distinctUntilChanged()
    }

    //used to handle back pressure ( i.e when there's a fast producer AKA upstream flow and a slow consumer AKA downstream flow) by dropping intermediate emissions
    //so it collects the first and latest emissions
    private fun showConflateOperator(): Flow<String> {
        return flowItems4.conflate()
    }


    //filters out rapid successive emissions and only emits the latest value after a specified time window
    private fun showDebounceOperator(): Flow<String> {
        return flowItems4.debounce(100)
    }

    //does not really change the flow of items but is rather used for performance
    //Allows the flow to run asynchronously when there is a slow consumer it creates a separate coroutine to collect items from the upward stream in parallel  based on the specified 'capacity'
    private fun showBufferOperator(): Flow<String> {
        return flowItems4.buffer(3)
    }

    //when the emissions finishes
    private fun showOnCompletionOperator(): Flow<String> {
        return flowItems4.onCompletion { Timber.d("emission complete ") }
    }

    //catches any error that happens in the upstream flow and emits them downstream
    private fun showCatchOperator(): Flow<String> {
        return getFLowWithException().catch { exception -> emit("exception caught : $exception") }
    }

    //performs some operation on every emitted value
    private fun showOnEachOperator(): Flow<String> {
        return flowItems3.onEach { delay(1000) }
    }

    //emits when the processing of the flow starts
    private fun showOnStartOperator(): Flow<String> {
        return flowItems3.onStart { Timber.d("emission started ") }
    }

    //it combines the most recently emitted values by each flow.
    //similar to zip operator but would always emit even if there is no emission from one of the input flows
    //using the 'combine(...) function you could combine up to 5 flows ie :
    //  combine(flow1,flow2,flow3,flow4){ v1,v2,v3,v4,v5 ->
    //      code to combine the flows to a single flow
    //  }
    private fun showCombineOperator(): Flow<String> {
        return flowItems2.onEach { delay(100) }.combine(flowItems3) { a, b ->
            "flow1 item : $a | flow2 item : $b"
        }
    }

    //combines elements from multiple flows into pairs
    //aligns elements from input flows (i.e flowItems2 and flowItems2) based on their emission order and only emits when all input flows have emitted an item
    //where emission order -> { 1st element from 1st input flow with 1st element from second flow ...  e.t.c}
    private fun showZipOperator(): Flow<String> {
        return flowItems2.zip(flowItems3) { a, b ->
            "flow1 item : $a | flow2 item : $b"
        }
    }

    //Flattens multiple flows into a sing sequential flow where only the last emission is considered
    //when source Flow emits a new item, ongoing transformations for previous items are canceled and only the transformation for the latest item is considered
    //useful when you want to work with the latest data and cancel any ongoing work related to previous data when new data arrives
    private fun showFlatmapLatestOperator(): Flow<String> {
        return flowItems2.flatMapLatest { score ->
            flow {
                emit("start value : $score")
                delay(1000)
                emit("end value : $score")
            }
        }
    }

    //used to flatten multiple flows into a sing sequential flow where the order of emission is not maintained
    //that is elements from different nested flows could interleave based on their completion times
    //best used in scenarios where you want to concurrently process the items and flatten to a single flow without caring about the order of emission
    private fun showFlatmapMergeOperator(): Flow<String> {
        return flowItems1.flatMapMerge { score ->
            flow {
                emit("start value : $score")
                delay(1000)
                emit("end value : $score")
            }
        }
    }

    //used to flatten multiple flows into a sing sequential flow where the order of emission is maintained
    //that is the elements of the first flow is emitted first before the elements of the second (nested) flow
    private fun showFlatmapConcatOperator(): Flow<String> {
        return flowItems1.flatMapConcat { score ->
            //flattens to a flow of string type
            flow {
                emit("actual score : $score")
            }
        }
    }

    //performs the same operation as 'reduce' flow operator but the item's type and result type must not be the same (result can be of any type)
    //and also you can specify the initial value
    private fun showFoldOperator(): Flow<String> {
        val initialFoldValue = 0

        return flow {
            val result = flowItems1.fold(initialFoldValue) { accumulator, value ->
                Timber.d("accumulator value = $accumulator | value = $value")
                accumulator + value
            }
            emit("total scores : $result")
        }
    }

    //performs an operation all over the items to reduce them to one item (item's type and result type must be the same !!)
    //reduce is a terminal operator so it would initiate the collection of the flow
    private fun showReduceOperator(): Flow<String> {
        return flow {
            val result = flowItems1.reduce { accumulator, value ->
                Timber.d("accumulator value = $accumulator | value = $value")
                accumulator + value
            }
            emit("total scores : $result")
        }
    }

    //only takes the first 'n' values via 'take' operator
    private fun showTakeOperator(): Flow<String> {
        return flowItems4.take(3).map { name -> "accepted names : $name" }
    }

    //transforms the values via 'map' operator
    private fun showMapOperator(): Flow<String> {
        return flowItems1.map { grade -> "user score : $grade" }
    }

    //filters the values via 'filter' operator
    private fun showFilterOperator(): Flow<String> {
        return flowItems1.filter { grade -> grade >= 25 }.map { "student pass score : $it" }
    }

    //custom transformations for each emitted value ( for each emission it goes through the custom transformation lambda block )
    //(it's different from 'map' operator in the sense that it can emit zero or more elements for each emitted item)
    //
    private fun showTransformOperator(): Flow<String> {
        return flowItems1.transform { emittedValue ->
            ///// custom transformations /////
            emit("first transformed emitted value $emittedValue")

            delay(1000) //simulate async work

            emit("second transformed emitted value $emittedValue")
        }
    }
}