package com.android.android_practice.recyclerview

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.android_practice.databinding.StudentHeaderItemBinding
import com.android.android_practice.recyclerview.SectionedItem.*

class SectionHeaderViewHolder (private val itemBinding: StudentHeaderItemBinding) :
    ViewHolder(itemBinding.root) {

    fun bind(header: HeaderItems) {
        itemBinding.headerTitle.text = header.header.toString()
    }
}