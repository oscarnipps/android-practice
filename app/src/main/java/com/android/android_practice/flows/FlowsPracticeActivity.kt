package com.android.android_practice.flows

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.android_practice.R
import com.android.android_practice.databinding.ActivityFlowsPracticeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/*
 * NOTES:
 *
 * - a flow producer emits via 'emit()' while the consumer collects via '.collect{}' on the flow
 *
 * - flows can be created by various ways
 *       -> flowOf(..)
 *       -> asFlow() on a collection
 *       -> flow builder : 'flow<ReturnType>{...}' also suspend functions can be called within the flow builder
 *
 * - Upward stream refer to the producer source of the flow and the operators before the current one
 *
 * - Downward stream refers to all that happens after the current operator (a flow can also be a collector)
 *
 * - terminal operators (collect, reduce, count, launchIn etc.) are used to to start listening for values
 *
 * - intermediate operators sets up operations that are executed lazily (executed when the consumer collects the data via terminal operators)
 *
 * - flows are cold flows as they created on demand but only emit data when they are observed / collected via terminal operators
 *
 * - cold flows restarts everytime it's collected for the first time
 *
 * - flows should be observed in a way that takes into account the lifecycle
 *       -> repeatOnLifecycle(state) { ... } automatically launches a new coroutine with the block passed to it when it gets to the specified state and when
 *           the lifecycle falls below that state , it cancels the coroutine (best to use this when the activity is initialized i.e in onCreate).
 *           (NOTE : coroutine that calls repeatOnLifecycle won't resume executing until the lifecycle is DESTROYED , so if you need to collect from
 *           multiple flows, create multiple coroutines using launch inside the repeatOnLifecycle block)
 *
 *       -> flowWithLifecycle(lifecycle,state) called on the producer source then collected. This is used when you only need to collect one flow in the ui
 *          (emits items and cancels the producer when the lifecycle moves in and out of the specified / targeted state)
 *
 *       -> lifecycleScope.launch not recommended as updates still happen when the app is in the background (could be handled manually in lifecycle methods i.e
 *          stop collecting in onStop and start collecting in onStart)
 *
 *       -> lifecycleScope.launchWhenX (where x is the target state i.e launchWhenStarted) which is better than lifecycleScope.launch as it suspends the collection
 *          when the app is in the background. Also not recommended as it keeps the producer active and potentially emitting items that can fill the memory with items
 *          that would not be displayed in the screen
 *
 * - state flow holds state / data which always holds one value (unlike flows where you can emit multiple values from within a flow i.e flow builder can can have multiple
 *   emissions via emit() calls)
 *
 * - state flows are hot flows (TIP : expose data as stateFlow from the viewModel or use asLiveData())
 *
 * - state flows are hot flows so when updates (i.e events or state updates) are sent and there are no collectors the update is lost
 *
 * - always use collectLatest{..} instead of collect{..} for stateFlows as it reflects the latest change of the state
 *
 * - sharedFlow is used to handle events (TIP : use collect{} and not collectLatest{} to consume events so as not to drop events )
 *
 * - when events are sent via sharedFlow and there are no collectors / consumers at that point , enable the replay cache ( i.e setting the replay value
 *   to greater than 0 to depict the amount of emissions that would be cached ) which would replay the emission to the following consumers
 *
 * - channels are for inter-coroutine communication
 *
 */
class FlowsPracticeActivity : AppCompatActivity() {

    private val viewModel: FlowsPracticeViewModel by viewModels()

    private lateinit var binding: ActivityFlowsPracticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_flows_practice)

        viewModel.getUiState1()
        showUiStateWithFlowWithOperator()

        //showUiStateWithRepeatOnLifecycle()

        //showWithConfiguredStateFlow()

        //showFlowOperators()
    }

    private fun showUiStateWithRepeatOnLifecycle() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState2.collectLatest {
                    updateUi(it)
                }
            }
        }
    }

    //used when you are only collecting one flow (it takes the lifecycle into account)
    private fun showUiStateWithFlowWithOperator() {
        lifecycleScope.launch {
            viewModel.uiState1.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                updateUi(it)
            }
        }
    }

    private fun showWithConfiguredStateFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState2.collectLatest {
                    updateUi(it)
                }
            }
        }
    }

    private fun showFlowOperators() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showFlowOperator().collect {
                    Timber.d(it)
                }
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
}