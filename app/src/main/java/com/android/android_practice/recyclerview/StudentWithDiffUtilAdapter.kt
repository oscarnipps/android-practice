package com.android.android_practice.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.android_practice.R
import com.android.android_practice.databinding.StudentItemBinding

/*
* uses diff util callback with listAdapter to optimize the the list loading display by calculating
* the updates when new items are added to the recyclerview on ( the difference calculations are done on the main thread)
* */
class StudentWithDiffUtilAdapter : ListAdapter<Student,StudentWithDiffUtilAdapter.StudentWithDiffUtilViewHolder> (StudentDiffUtilCallback()){

    private lateinit var binding :StudentItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentWithDiffUtilViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.student_item,parent,false)
        return StudentWithDiffUtilViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentWithDiffUtilViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StudentWithDiffUtilViewHolder(private val itemBinding: StudentItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(student: Student) {
            itemBinding.name.text = student.name
            itemBinding.age.text = student.age.toString()
        }
    }

    //used ItemCallback , however you could also use DifUtil.Callback which would override more methods (i.e getOldListSize and getNewListSize)
    class StudentDiffUtilCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            //uses the .equals() value to check if the contents are the same
            //== checks for structural equality while === checks for referential equality
            return oldItem == newItem
        }
    }
}