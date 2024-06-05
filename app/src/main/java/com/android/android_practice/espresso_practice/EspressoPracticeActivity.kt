package com.android.android_practice.espresso_practice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.android_practice.PracticeAppEspressoIdlingResource
import com.android.android_practice.PracticeEspressoCountingIdlingResource
import com.android.android_practice.R
import com.android.android_practice.databinding.ActivityEspressoPracticeBinding
import com.android.android_practice.recyclerview.Student
import com.android.android_practice.recyclerview.StudentRecyclerViewAdapter
import kotlinx.coroutines.launch

class EspressoPracticeActivity : AppCompatActivity(),
    StudentRecyclerViewAdapter.StudentRecyclerViewAdapterItemListener {

    private lateinit var binding: ActivityEspressoPracticeBinding

    private lateinit var studentRecyclerViewAdapter: StudentRecyclerViewAdapter

    private var repo = EspressoPracticeRepo()

    val testIdlingResource = PracticeAppEspressoIdlingResource("test")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_espresso_practice)

        //setUpBasicRecyclerView()

        setUpRecyclerView()

        setUpRecyclerViewWithFlowItems()

    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.studentRecyclerView

        studentRecyclerViewAdapter = StudentRecyclerViewAdapter(this)

        recyclerView.adapter = studentRecyclerViewAdapter
    }

    private fun setUpRecyclerViewWithFlowItems() {
        PracticeEspressoCountingIdlingResource.increment()
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                repo.getStudentsFlow().collect {
                    //update the list via notifyDataSetChanged
                    studentRecyclerViewAdapter.setItems(it)
                    PracticeEspressoCountingIdlingResource.decrement()
                }
            }
        }
    }

    private fun setUpBasicRecyclerView() {
        val recyclerView = binding.studentRecyclerView

        studentRecyclerViewAdapter = StudentRecyclerViewAdapter(this)

        recyclerView.adapter = studentRecyclerViewAdapter

        //update the list via notifyDataSetChanged
        studentRecyclerViewAdapter.setItems(repo.getStudents())
    }

    override fun onStudentRecyclerItemClicked(student: Student) {
        Toast.makeText(this, student.name, Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(this, SecondEspressoPracticeActivity::class.java).apply {
                putExtra("name", student.name)
                putExtra("id", student.id)
                putExtra("age", student.age)
            }
        )
    }
}