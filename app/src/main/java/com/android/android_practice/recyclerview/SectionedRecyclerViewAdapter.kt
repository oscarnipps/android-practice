package com.android.android_practice.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.android_practice.R
import com.android.android_practice.databinding.StudentHeaderItemBinding
import com.android.android_practice.databinding.StudentItemBinding

class SectionedRecyclerViewAdapter : ListAdapter<SectionedItem, ViewHolder>(StudentDiffUtil()) {

    companion object {
        private const val SECTION_HEADER_TYPE = 0
        private const val SECTION_ITEM_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            SECTION_HEADER_TYPE -> SectionHeaderViewHolder(
                StudentHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> SectionItemsViewHolder(
                StudentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SectionedItem.HeaderItems -> SECTION_HEADER_TYPE
            is SectionedItem.SectionItems -> SECTION_ITEM_TYPE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is SectionHeaderViewHolder -> {
                holder.bind(getItem(position) as SectionedItem.HeaderItems)
            }

            is SectionItemsViewHolder -> {
                holder.bind(getItem(position) as SectionedItem.SectionItems)
            }
        }
    }


    class StudentDiffUtil : DiffUtil.ItemCallback<SectionedItem>() {

        override fun areItemsTheSame(oldItem: SectionedItem, newItem: SectionedItem): Boolean {
            if (oldItem is SectionedItem.HeaderItems && newItem is SectionedItem.HeaderItems) {
                return  oldItem.header == newItem.header
            }

            if (oldItem is SectionedItem.SectionItems && newItem is SectionedItem.SectionItems) {
                return oldItem.student.id == newItem.student.id
            }

            return  true
        }

        override fun areContentsTheSame(oldItem: SectionedItem, newItem: SectionedItem): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }

}