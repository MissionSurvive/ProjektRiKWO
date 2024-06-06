package com.example.meetingapp.viewHolders

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.meetingapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ExtraOptionTextViewHolder(itemView: View) : ViewHolder(itemView) {
    val optionName: TextView = itemView.findViewById(R.id.optionName)
    val optionContent: TextView = itemView.findViewById(R.id.optionContent)

    fun bind(id: Int, name: String, content: String) {
        optionName.text = name
        optionContent.text = content
    }

    companion object {
        fun create(parent: ViewGroup): ExtraOptionTextViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.extra_options_text_list_view, parent, false)
            return ExtraOptionTextViewHolder(view)
        }
    }
}