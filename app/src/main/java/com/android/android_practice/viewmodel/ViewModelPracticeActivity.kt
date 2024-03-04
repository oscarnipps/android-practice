package com.android.android_practice.viewmodel

import android.os.Bundle
import android.provider.ContactsContract.Contacts.Data
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.android_practice.R
import com.android.android_practice.databinding.ActivityViewModelPracticeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewModelPracticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewModelPracticeBinding

    /*
    can also use shared view models i.e share view models between multiple fragments :
    private val practiceViewModel : PracticeViewModel by activityViewModels()

    you can also scope to a particular fragment the ViewModel is scoped to the parent of `this` Fragment
    val viewModel: SharedViewModel by viewModels(ownerProducer = { requireParentFragment() })

   */
    private val practiceViewModel : PracticeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_model_practice)

        binding.counterBtn.setOnClickListener {
            //practiceViewModel.updateCounter()
            practiceViewModel.updateCounterViaSavedStateHandle()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                practiceViewModel.uiState.collectLatest { state ->
                    binding.counter.text = state.counter.toString()
                    binding.counterMessage.text = getString(R.string.counter_message,state.counter)
                }
            }
        }

    }
}