package com.android.android_practice.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.android_practice.R
import com.android.android_practice.databinding.StudentItemBinding


//calculates the updates when new items are added to the recyclerview , however asyncListDiffer does the calculation on a background thread
class StudentWithAsyncListDifferAdapter :
    RecyclerView.Adapter<StudentWithAsyncListDifferAdapter.StudentWithAsyncListDifferViewHolder>() {

    private lateinit var binding: StudentItemBinding

    class StudentWithAsyncListDifferViewHolder(private val itemBinding: StudentItemBinding) :
        ViewHolder(itemBinding.root) {

        fun bind(student: Student) {
            itemBinding.name.text = student.name
            itemBinding.age.text = student.age.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentWithAsyncListDifferViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.student_item,
            parent,
            false
        )
        return StudentWithAsyncListDifferViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: StudentWithAsyncListDifferViewHolder,
        position: Int
    ) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    //provides ability to do custom logic on the list items
    fun submitList(list: List<Student>) {
        asyncListDiffer.submitList(list)
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            //uses the .equals() value to check if the contents are the same
            //== checks for structural equality while === checks for referential equality
            return oldItem == newItem
        }
    }

    //differ which would update the list asynchronously
    private val asyncListDiffer = AsyncListDiffer(this, diffUtilCallback)
}