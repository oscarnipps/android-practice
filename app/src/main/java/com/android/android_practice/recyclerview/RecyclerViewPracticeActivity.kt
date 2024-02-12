package com.android.android_practice.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.android.android_practice.R
import com.android.android_practice.databinding.ActivityRecyclerViewPracticeBinding
import com.android.android_practice.databinding.StudentItemBinding

class RecyclerViewPracticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewPracticeBinding

    private lateinit var studentRecyclerViewAdapter: StudentRecyclerViewAdapter

    private lateinit var studentWithDiffUtilAdapter: StudentWithDiffUtilAdapter

    private lateinit var studentWithAsyncListDifferAdapter: StudentWithAsyncListDifferAdapter

    private var studentList = getStudents()

    //1 -> basic recyclerview set up , 2 -> recyclerview with diff util , 3 -> recyclerview with async list differ
    private val adapterFlag = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view_practice)


        when (adapterFlag) {
            1 -> setUpBasicRecyclerView()

            2 -> setUpRecyclerViewWithDiffUtil()

            3 -> setUpRecyclerViewWithAsyncListDiffer()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchValue: String?): Boolean {
                filterStudentList(searchValue)
                return true
            }

        })
    }

    private fun filterStudentList(query: String?) {
        val filteredList = mutableListOf<Student>()

        if (query != null) {

            for (student in studentList) {
                //can have custom requirement if the check against the query text typed fails
                if (student.name.lowercase().contains(query) || student.age.toString().contains(query)) {
                    filteredList.add(student)
                }
            }

            //can have custom requirement if the check against the query text typed fails
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "student not found", Toast.LENGTH_SHORT).show()
            } else {

                //update the items in the adapter
                when (adapterFlag) {
                    1 -> studentRecyclerViewAdapter.setItems(filteredList)

                    2 -> studentWithDiffUtilAdapter.submitList(filteredList)

                    3 -> studentWithAsyncListDifferAdapter.submitList(filteredList)
                }
            }

        }
    }

    private fun setUpBasicRecyclerView() {
        val recyclerView = binding.studentRecyclerView

        studentRecyclerViewAdapter = StudentRecyclerViewAdapter()

        recyclerView.adapter = studentRecyclerViewAdapter

        //update the list via notifyDataSetChanged
        studentRecyclerViewAdapter.setItems(studentList)
    }

    private fun setUpRecyclerViewWithDiffUtil() {
        val recyclerView = binding.studentRecyclerView

        studentWithDiffUtilAdapter = StudentWithDiffUtilAdapter()

        recyclerView.adapter = studentWithDiffUtilAdapter

        //update the list via list adapter's submitList() method
        studentWithDiffUtilAdapter.submitList(studentList)
    }

    private fun setUpRecyclerViewWithAsyncListDiffer() {
        val recyclerView = binding.studentRecyclerView

        studentWithAsyncListDifferAdapter = StudentWithAsyncListDifferAdapter()

        recyclerView.adapter = studentWithAsyncListDifferAdapter

        //update the list via asyncListDiffer submitList() method
        studentWithAsyncListDifferAdapter.submitList(studentList)
    }

    private fun getStudents(): List<Student> {
        return mutableListOf(
            Student(1, "James Bun", 23),
            Student(2, "Harvey Barnes bun", 19),
            Student(3, "Bob White", 26),
            Student(4, "Jacob Murphy", 25),
            Student(5, "Lukas pope", 22),
            Student(6, "richard nick", 30),
            Student(7, "phillip burn", 26),
            Student(8, "donald duke", 21),
            Student(9, "Dave shook", 20),
        )
    }


}