package com.android.android_practice.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.android_practice.R
import com.android.android_practice.databinding.StudentItemBinding

//using basic recyclerview
class StudentRecyclerViewAdapter (): RecyclerView.Adapter<StudentRecyclerViewAdapter.StudentViewHolder>() {

    private lateinit var binding :StudentItemBinding
    private var students  = mutableListOf<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.student_item,parent,false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    fun setItems(studentsList : List<Student>) {
        students = studentsList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    inner class StudentViewHolder(private val itemBinding: StudentItemBinding) : ViewHolder(itemBinding.root){
        fun bind(student: Student) {
            itemBinding.name.text = student.name
            itemBinding.age.text = student.age.toString()
        }

    }
}