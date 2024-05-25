package com.example.meetingapp.adapters
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.meetingapp.CustomOptions
import com.example.meetingapp.viewHolders.ExtraOptionTextViewHolder
import com.example.meetingapp.viewHolders.ExtraOptionViewHolder

class ExtraOptionsTextListAdapter() : ListAdapter<CustomOptions, ExtraOptionTextViewHolder>(OptionsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraOptionTextViewHolder {
        return ExtraOptionTextViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ExtraOptionTextViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.optionId, current.optionKey, current.optionValue)
    }

    class OptionsComparator : DiffUtil.ItemCallback<CustomOptions>() {
        override fun areItemsTheSame(oldItem: CustomOptions, newItem: CustomOptions): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CustomOptions, newItem: CustomOptions): Boolean {
            return oldItem.optionId == newItem.optionId &&
                    oldItem.optionKey == newItem.optionKey &&
                    oldItem.optionValue == newItem.optionValue
        }
    }
}