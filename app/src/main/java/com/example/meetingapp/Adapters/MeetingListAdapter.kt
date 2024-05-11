package com.example.meetingapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingapp.Items.DateItem
import com.example.meetingapp.Items.MeetingItem
import com.example.meetingapp.R

class MeetingListAdapter(private val meetingItems: List<MeetingItem>) : RecyclerView.Adapter<MeetingListAdapter.MeetingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meetings_list_item_view, parent, false)
        return MeetingViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val meetingItem = meetingItems[position]
        holder.bind(meetingItem)
    }

    override fun getItemCount(): Int = meetingItems.size

    inner class MeetingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.text)
        private val textView2: TextView = itemView.findViewById(R.id.nameText)

        fun bind(meetingItem: MeetingItem) {
            val dayString = if (meetingItem.day < 10) "0${meetingItem.day}" else meetingItem.day.toString()
            val monthString = if (meetingItem.month < 10) "0${meetingItem.month}" else meetingItem.month.toString()
            val startMinuteString = if (meetingItem.startMinute < 10) "0${meetingItem.startMinute}" else meetingItem.startMinute.toString()
            val endMinuteString = if (meetingItem.endMinute < 10) "0${meetingItem.endMinute}" else meetingItem.endMinute.toString()

            textView.text = "$dayString.$monthString.${meetingItem.year} ${meetingItem.startHour}:${startMinuteString} - ${meetingItem.endHour}:${endMinuteString}"
            textView2.text = meetingItem.name
        }
    }
}