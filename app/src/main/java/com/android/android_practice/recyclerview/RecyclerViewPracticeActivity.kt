package com.android.android_practice.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import com.android.android_practice.R
import com.android.android_practice.databinding.ActivityRecyclerViewPracticeBinding

class RecyclerViewPracticeActivity : AppCompatActivity() ,
    StudentRecyclerViewAdapter.StudentRecyclerViewAdapterItemListener {

    private lateinit var binding: ActivityRecyclerViewPracticeBinding

    private lateinit var studentRecyclerViewAdapter: StudentRecyclerViewAdapter

    private lateinit var studentHeaderRecyclerViewAdapter: StudentHeaderRecyclerViewAdapter

    private lateinit var studentFooterRecyclerViewAdapter: StudentFooterRecyclerViewAdapter

    private lateinit var sectionedRecyclerViewAdapter: SectionedRecyclerViewAdapter

    private lateinit var concatViewAdapter: ConcatAdapter

    private lateinit var studentWithDiffUtilAdapter: StudentWithDiffUtilAdapter

    private lateinit var studentWithAsyncListDifferAdapter: StudentWithAsyncListDifferAdapter

    private var studentList = getStudents()

    //1 -> basic recyclerview set up ,
    //2 -> recyclerview with diff util ,
    //3 -> recyclerview with async list differ
    //4 -> recyclerview with multiple adapters
    private val adapterFlag = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view_practice)

        when (adapterFlag) {
            1 -> setUpBasicRecyclerView()

            2 -> setUpRecyclerViewWithDiffUtil()

            3 -> setUpRecyclerViewWithAsyncListDiffer()

            4 -> setUpRecyclerViewWithDifferentAdapters()

            5 -> setUpSectionedRecyclerViewAdapter()
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
                if (student.name.lowercase().contains(query) || student.age.toString()
                        .contains(query)
                ) {
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

                    4 -> {
                        //todo update adapter with list items
                    }
                }
            }

        }
    }

    private fun setUpSectionedRecyclerViewAdapter() {
        sectionedRecyclerViewAdapter = SectionedRecyclerViewAdapter()

        val recyclerView = binding.studentRecyclerView

        recyclerView.adapter = sectionedRecyclerViewAdapter

        val items = getStudents().sortedBy { it.name }
            .groupBy { SectionedItem.HeaderItems(it.name.uppercase()[0]) }
            .flatMap { (key, value) ->
                listOf(key) + value.map { SectionedItem.SectionItems(it) }
            }


        sectionedRecyclerViewAdapter.submitList(items)
    }

    private fun toPurchaseItem(student: Student): SectionedItem {
        return SectionedItem.SectionItems(student)
    }

    private fun setUpRecyclerViewWithDifferentAdapters() {
        //populate / initialize adapters
        studentRecyclerViewAdapter = StudentRecyclerViewAdapter(this)

        studentHeaderRecyclerViewAdapter = StudentHeaderRecyclerViewAdapter()

        studentHeaderRecyclerViewAdapter.setItems(StudentMapper.mapToStudentHeader(studentList))

        studentRecyclerViewAdapter.setItems(studentList)

        //todo: implement footer later when needed
        //studentFooterRecyclerViewAdapter = StudentFooterRecyclerViewAdapter()


        //add as much adapter's as possible
        //order of adapters also important
        //also has possibility to remove and add adapters
        concatViewAdapter = ConcatAdapter(
            studentHeaderRecyclerViewAdapter,
            studentRecyclerViewAdapter
        )

        val recyclerView = binding.studentRecyclerView

        recyclerView.adapter = concatViewAdapter
    }

    private fun setUpBasicRecyclerView() {
        val recyclerView = binding.studentRecyclerView

        studentRecyclerViewAdapter = StudentRecyclerViewAdapter(this)

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

    override fun onStudentRecyclerItemClicked(student: Student) {
        Toast.makeText(this, student.name,Toast.LENGTH_SHORT).show()
    }
}