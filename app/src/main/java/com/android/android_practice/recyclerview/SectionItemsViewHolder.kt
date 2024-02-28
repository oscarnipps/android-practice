package com.android.android_practice.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.android.android_practice.databinding.StudentHeaderItemBinding
import com.android.android_practice.databinding.StudentItemBinding
import com.android.android_practice.recyclerview.SectionedItem.*

class SectionItemsViewHolder(private val itemBinding: StudentItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(sectionItem: SectionItems) {
        itemBinding.name.text = sectionItem.student.name
        itemBinding.age.text = sectionItem.student.age.toString()
    }
}