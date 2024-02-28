package com.android.android_practice.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.android_practice.databinding.StudentHeaderItemBinding

class StudentHeaderRecyclerViewAdapter() : RecyclerView.Adapter<StudentHeaderRecyclerViewAdapter.HeaderViewHolder>() {

    private var studentHeader  = mutableListOf<StudentHeader>()
    
    inner class HeaderViewHolder(private val itemBinding: StudentHeaderItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        
        fun bind(studentHeader: StudentHeader) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
       val binding = StudentHeaderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HeaderViewHolder(binding)
    }

    fun setItems(studentHeaderList: Map<Char, List<Student>>) {
        //todo: update items or use list adapter with diff util
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return studentHeader.size
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(studentHeader[position])
    }
}