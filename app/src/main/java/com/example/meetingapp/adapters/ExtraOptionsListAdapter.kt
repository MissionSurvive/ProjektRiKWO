package com.example.meetingapp.adapters
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.meetingapp.CustomOptions
import com.example.meetingapp.viewHolders.ExtraOptionViewHolder

class ExtraOptionsListAdapter(extraOptions: MutableList<CustomOptions>) : ListAdapter<CustomOptions, ExtraOptionViewHolder>(OptionsComparator()) {
    val extraOptions = extraOptions
    var isTextEmpty: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraOptionViewHolder {
        return ExtraOptionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ExtraOptionViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.optionId, current.optionKey, current.optionValue)
        holder.deleteButton.setOnClickListener {
            extraOptions.removeAt(position)
            notifyItemRemoved(position)
        }
        holder.nameTextInputEditText.doOnTextChanged { text, start, before, count ->
            if(!text.isNullOrEmpty()) {
                isTextEmpty = false
                extraOptions[position].optionValue = text.toString()
            }
            else {
                isTextEmpty = true
            }

        }
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