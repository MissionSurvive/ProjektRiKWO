package com.example.meetingapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingapp.Converters.Converters
import com.example.meetingapp.Items.DateItem
import com.example.meetingapp.Items.MeetingItem
import com.example.meetingapp.MainActivity
import com.example.meetingapp.Meetings
import com.example.meetingapp.MeetingsWithCustomOptions
import com.example.meetingapp.R
import java.util.Calendar

class DateListAdapter(private val dateItems: List<DateItem>, meetings:List<MeetingsWithCustomOptions>, context: Context) : RecyclerView.Adapter<DateListAdapter.DateViewHolder>() {
    val context = context
    private lateinit var meetingListAdapter: MeetingListAdapter
    private var meetings: List<MeetingsWithCustomOptions> = meetings
    val converters = Converters()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meetings_list_view, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val meetingsByDate = meetings.groupBy { meeting ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = converters.fromDateToLong(meeting.meetings.meetingDatetime)!!
            calendar.get(Calendar.DAY_OF_MONTH)
        }
        val dateItem = dateItems[position]
        holder.bind(dateItem)
            val day = holder.textView.text.toString().split(" ").first().toInt()
            val meetingsForDay = meetingsByDate[day]?.map { MeetingItem(it.meetings.meetingId.toLong(), it.meetings.meetingName) } ?: emptyList()
            val meetingAdapter = MeetingListAdapter(meetingsForDay, context)
            holder.meetingRecyclerView.adapter = meetingAdapter
            holder.meetingRecyclerView.layoutManager = LinearLayoutManager(null)
    }

    override fun getItemCount(): Int = dateItems.size

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val meetingRecyclerView: RecyclerView = itemView.findViewById(R.id.meeting_list_item)

        fun bind(dateItem: DateItem) {
            textView.text = "${dateItem.day} ${dateItem.month}"
            // The MeetingListAdapter will be set in the MainActivity
        }
    }
}