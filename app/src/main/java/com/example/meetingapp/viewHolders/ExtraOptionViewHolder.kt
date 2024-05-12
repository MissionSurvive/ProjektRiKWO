package com.example.meetingapp.viewHolders

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.meetingapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ExtraOptionViewHolder(itemView: View) : ViewHolder(itemView) {
    val nameTextInputLayout: TextInputLayout = itemView.findViewById(R.id.contentTextInputLayout)
    val nameTextInputEditText: TextInputEditText = itemView.findViewById(R.id.contentInputEditText)
    val deleteButton: Button = itemView.findViewById(R.id.iconButton)

    fun bind(id: Int, name: String, content: String) {
        nameTextInputLayout.hint = name
        nameTextInputEditText.setText(content)
    }

    companion object {
        fun create(parent: ViewGroup): ExtraOptionViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.extra_options_list_view, parent, false)
            return ExtraOptionViewHolder(view)
        }
    }
}